/***********************************************************************
 * This file is part of iDempiere ERP Open Source                      *
 * http://www.idempiere.org                                            *
 *                                                                     *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 **********************************************************************/
package org.idempiere.extension.manager.form;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.concurrent.CompletableFuture;

import org.adempiere.base.Core;
import org.adempiere.base.markdown.IMarkdownRenderer;
import org.compiere.model.MExtension;
import org.compiere.model.MExtensionEntity;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.MPackageImp;
import org.compiere.model.MPackageImpDetail;
import org.compiere.model.Query;
import org.compiere.model.SystemProperties;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.idempiere.extension.manager.ExtensionManagerActivator;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExtensionBrowserService {

	private static final CLogger log = CLogger.getCLogger(ExtensionBrowserService.class);
	private HttpClient httpClient;

	public ExtensionBrowserService() {
		httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
	}

	/**
	 * Fetch extensions from the iDempiere Extension Repository
	 * @return JsonArray of extensions
	 * @throws Exception
	 */
	public JsonArray fetchRepositoryExtensions() throws Exception {
		String repoUrl = SystemProperties.getIDempiereRepositoryUrl();
		if (repoUrl == null || repoUrl.trim().isEmpty()) {
			throw new IllegalStateException(Msg.getMsg(Env.getCtx(), "ExtensionRepositoryNotSet")); //iDempiere extension repository URL is not set
		}

		String indexUrl = toRawGithubURL(repoUrl, "index.json");

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(indexUrl)).GET().build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		
		if (response.statusCode() == 200) {
			JsonObject indexObj = JsonParser.parseString(response.body()).getAsJsonObject();
			return indexObj.getAsJsonArray("extensions");
		} else {
			throw new RuntimeException(Msg.getMsg(Env.getCtx(), "HttpFetchFailed", new Object[] {response.statusCode(), "index.json"})); //Http {0} fetching {1}
		}
	}

	/**
	 * Get installed extensions
	 * @return List of installed extensions
	 */
	public List<MExtension> getInstalledExtensions() {
		return new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionState IN (?,?)", null)
				.setParameters(MExtension.EXTENSIONSTATE_Installed, MExtension.EXTENSIONSTATE_Disabled)
				.setOnlyActiveRecords(true)
				.list();
	}

	/**
	 * Fetch and render markdown to html
	 * @param url URL to fetch markdown from
	 * @return Rendered markdown
	 */
	public String fetchAndRenderMarkdown(String url) {
		if (url == null || url.isEmpty()) return "";
		
		String rawUrl = toRawGithubPath(url);
		
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(rawUrl)).GET().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			String markdown = "";
			if (response.statusCode() == 200) {
				markdown = response.body();
			} else {
				throw new Exception(Msg.getMsg(Env.getCtx(), "HttpFetchFailed", new Object[] {response.statusCode(), rawUrl})); //Http {0} fetching {1}
			}
			
			IMarkdownRenderer renderer = Core.getMarkdownRenderer();
			String baseUrl = rawUrl;
			if (baseUrl.contains("/")) {
				baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf('/') + 1);
			}
			return renderer.renderToHtml(markdown, true, baseUrl);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to fetch or render documentation from " + rawUrl, e);
			return "<div style=\"padding: 20px; color: #721c24; background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 4px;\">" +
					"<h4>%s</h4><p>" + e.getMessage() + "</p></div>".formatted(Msg.getMsg(Env.getCtx(), "Error"));
		}
	}

	/**
	 * Convert github repository URL to raw github URL
	 * @param repoUrl
	 * @param relativePath
	 * @return
	 */
	public String toRawGithubURL(String repoUrl, String relativePath) {
		if (repoUrl.contains("github.com"))
			repoUrl = repoUrl.replace("github.com", "raw.githubusercontent.com");
		String rawUrl = repoUrl.endsWith("/") ? repoUrl + "main/" + relativePath : repoUrl + "/main/" + relativePath;
		return rawUrl;
	}

	/**
	 * Convert github absolute URL to raw github absolute URL
	 * @param absolutePath
	 * @return
	 */
	private String toRawGithubPath(String absolutePath) {
		if (absolutePath.contains("github.com") && absolutePath.contains("/blob/")) {
			absolutePath = absolutePath.replace("github.com", "raw.githubusercontent.com").replace("/blob/", "/");
		}
		return absolutePath;
	}

	/**
	 * Uninstall all OSGi bundles of an extension
	 * @param extension
	 */
	public void uninstallBundles(ExtensionMetadata extension) {
		if (!extension.hasBundles()) return;
		JsonArray bundles = extension.getBundles();
		for (JsonElement bel : bundles) {
			JsonObject b = bel.getAsJsonObject();
			if (b.has("symbolicName")) {
				String symbolicName = b.get("symbolicName").getAsString();
				for (Bundle bundle : ExtensionManagerActivator.context.getBundles()) {
					if (symbolicName.equals(bundle.getSymbolicName())) {
						try {
							if (bundle.getState() != Bundle.UNINSTALLED) {
								if (bundle.getState() == Bundle.ACTIVE) {
									bundle.stop();
								}
								bundle.uninstall();
							}
						} catch (Exception e) {
							log.log(Level.WARNING, "Stop/Uninstall failed for " + symbolicName, e);
						}
					}
				}
			}
		}
	}

	/**
	 * Validate optional database requirement
	 * @param extension
	 * @return error message or null if fulfill
	 * @throws SQLException
	 */
	public String validateDatabaseRequirement(ExtensionMetadata extension) throws SQLException {
		JsonArray array = extension.getDatabase();
		if (array == null || array.isEmpty())
			return null;

		JsonObject dbObject = null;
		for (JsonElement el : array) {
			if (el.isJsonObject()) {
				var jsonObject = el.getAsJsonObject();
				if (jsonObject.has("id")) {
					String id = jsonObject.get("id").getAsString();
					if (DB.isPostgreSQL() && "postgresql".equals(id))
						dbObject = jsonObject;
					else if (DB.isOracle() && "oracle".equals(id))
						dbObject = jsonObject;
				}
			}
			if (dbObject != null)
				break;
		}
		if (dbObject == null)
			return Msg.getMsg(Env.getCtx(), "DatabaseTypeNotSupported", new Object[] {DB.getDatabase().getName()}); //Database {0} not supported
		
		//validate version
		if (dbObject.has("version")) {
			String version = dbObject.get("version").getAsString();
			DatabaseMetaData dbmd = DB.getConnection().getMetaData();
			String fullVersion = dbmd.getDatabaseProductVersion();
			Version requiredVersion = Version.parseVersion(version);
			Version currentVersion = Version.parseVersion(fullVersion.indexOf(" ") > 0 ? fullVersion.substring(0, fullVersion.indexOf(" ")) : fullVersion);
			if (currentVersion.compareTo(requiredVersion) < 0)
				//{0} version {1} is too old, {2} and above is required
				return Msg.getMsg(Env.getCtx(), "DatabaseVersionTooOld", new Object[] {DB.getDatabase().getName(), currentVersion, requiredVersion});
		}

		//validate PostgreSQL extensions
		if (DB.isPostgreSQL() && dbObject.has("extensions")) {
			JsonArray dbExtensions = dbObject.getAsJsonArray("extensions");
			for (JsonElement el : dbExtensions) {
				String dbExtensionName = el.getAsString();
				int count = DB.getSQLValue(null, "SELECT count(*) FROM pg_available_extensions WHERE name = ?", dbExtensionName.trim());
				if (count == 0)
					//The required extension {0} is not found on this {1} server
					return Msg.getMsg(Env.getCtx(), "DatabaseExtensionNotFound", new Object[] {dbExtensionName, DB.getDatabase().getName()});
			}
		}

		return null;
	}

	/**
	 * Uninstall an extension
	 * @param extension
	 * @throws Exception
	 */
	public void uninstallExtension(ExtensionMetadata extension) throws Exception {
		uninstallBundles(extension);
		String extensionId = extension.getId();
		if (extensionId != null) {
			MExtension mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
					.setParameters(extensionId)
					.setOnlyActiveRecords(true)
					.first();
			if (mExtension != null) {
				mExtension.setExtensionState(MExtension.EXTENSIONSTATE_Uninstalled);
				mExtension.saveEx();
			}
		}
	}

	/**
	 * Disable and stop an extension
	 * @param extension
	 * @throws Exception
	 */
	public void disableExtension(ExtensionMetadata extension) throws Exception {
		if (extension.hasBundles()) {
			JsonArray bundles = extension.getBundles();
			for (JsonElement bel : bundles) {
				JsonObject b = bel.getAsJsonObject();
				if (b.has("symbolicName")) {
					String symbolicName = b.get("symbolicName").getAsString();
					for (Bundle bundle : ExtensionManagerActivator.context.getBundles()) {
						if (symbolicName.equals(bundle.getSymbolicName())) {
							bundle.stop();
						}
					}
				}
			}
		}
		String extensionId = extension.getId();
		if (extensionId != null) {
			MExtension mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
					.setParameters(extensionId)
					.first();
			if (mExtension != null) {
				mExtension.setExtensionState(MExtension.EXTENSIONSTATE_Disabled);
				mExtension.saveEx();
				
				List<MExtensionEntity> entities = new Query(Env.getCtx(), MExtensionEntity.Table_Name, "AD_Extension_ID=?", null)
						.setParameters(mExtension.getAD_Extension_ID())
						.list();
				for (MExtensionEntity entity : entities) {
					PO po = MTable.get(Env.getCtx(), entity.getAD_Table_ID()).getPOByUU(entity.getRecord_UU(), null);
					if (po != null) {
						po.set_Value("IsActive", false);
						po.saveEx();
					}
				}
			}
		}
		CacheMgt.get().reset();
	}

	/**
	 * Enable and start an extension
	 * @param extension
	 * @throws Exception
	 */
	public void enableExtension(ExtensionMetadata extension) throws Exception {
		if (extension.hasBundles()) {
			JsonArray bundles = extension.getBundles();
			for (JsonElement bel : bundles) {
				JsonObject b = bel.getAsJsonObject();
				if (b.has("symbolicName")) {
					String symbolicName = b.get("symbolicName").getAsString();
					for (Bundle bundle : ExtensionManagerActivator.context.getBundles()) {
						if (symbolicName.equals(bundle.getSymbolicName())) {
							bundle.start();
						}
					}
				}
			}
		}
		String extensionId = extension.getId();
		if (extensionId != null) {
			MExtension mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
					.setParameters(extensionId)
					.first();
			if (mExtension != null) {
				mExtension.setExtensionState(MExtension.EXTENSIONSTATE_Installed);
				mExtension.saveEx();
				
				List<MExtensionEntity> entities = new Query(Env.getCtx(), MExtensionEntity.Table_Name, "AD_Extension_ID=?", null)
						.setParameters(mExtension.getAD_Extension_ID())
						.list();
				for (MExtensionEntity entity : entities) {
					PO po = MTable.get(Env.getCtx(), entity.getAD_Table_ID()).getPOByUU(entity.getRecord_UU(), null);
					if (po != null) {
						po.set_Value("IsActive", true);
						po.saveEx();
					}
				}
			}
		}
		CacheMgt.get().reset();
	}

	/**
	 * Create extension archive (.idext)
	 * @param extension
	 * @return
	 * @throws Exception
	 */
	public File buildArchive(ExtensionMetadata extension) throws Exception {
		File tempFile = File.createTempFile("extension", ".idext");
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempFile))) {
			// 1. metadata.json
			zos.putNextEntry(new ZipEntry("metadata.json"));
			zos.write(extension.toString().getBytes(StandardCharsets.UTF_8));
			zos.closeEntry();

			// 2. info.md
			if (extension.hasInfoUrl()) {
				String infoUrl = extension.getInfoUrl();
				infoUrl = toRawGithubPath(infoUrl);
				HttpRequest reqInfo = HttpRequest.newBuilder().uri(URI.create(infoUrl)).GET().build();
				HttpResponse<byte[]> resInfo = httpClient.send(reqInfo, HttpResponse.BodyHandlers.ofByteArray());
				if (resInfo.statusCode() == 200) {
					zos.putNextEntry(new ZipEntry("info.md"));
					zos.write(resInfo.body());
					zos.closeEntry();
				}
			}

			// 3. CHANGELOG.md
			if (extension.hasChangeLogUrl()) {
				String changelogUrl = extension.getChangeLogUrl();
				changelogUrl = toRawGithubPath(changelogUrl);
				HttpRequest reqChangelog = HttpRequest.newBuilder().uri(URI.create(changelogUrl)).GET().build();
				HttpResponse<byte[]> resChangelog = httpClient.send(reqChangelog, HttpResponse.BodyHandlers.ofByteArray());
				if (resChangelog.statusCode() == 200) {
					zos.putNextEntry(new ZipEntry("CHANGELOG.md"));
					zos.write(resChangelog.body());
					zos.closeEntry();
				}
			}

			// 4. assets
			if (extension.hasAssets()) {
				JsonArray assets = extension.getAssets();
				for (JsonElement ael : assets) {
					JsonObject a = ael.getAsJsonObject();
					if (a.has("url") && a.has("name")) {
						String aUrl = a.get("url").getAsString();
						aUrl = toRawGithubPath(aUrl);
						try {
							HttpRequest reqAsset = HttpRequest.newBuilder().uri(URI.create(aUrl)).GET().build();
							HttpResponse<byte[]> resAsset = httpClient.send(reqAsset, HttpResponse.BodyHandlers.ofByteArray());
							if (resAsset.statusCode() == 200) {
								zos.putNextEntry(new ZipEntry("assets/" + a.get("name").getAsString()));
								zos.write(resAsset.body());
								zos.closeEntry();
							}
						} catch (Exception e) {
							log.log(Level.WARNING, "Failed to fetch asset: " + aUrl, e);
						}
					}
				}
			}

			// 5. bundles
			if (extension.hasBundles()) {
				JsonArray bundles = extension.getBundles();
				for (JsonElement bel : bundles) {
					JsonObject b = bel.getAsJsonObject();
					if (b.has("downloadUrl")) {
						String dUrl = b.getAsJsonPrimitive("downloadUrl").getAsString();
						HttpRequest reqJar = HttpRequest.newBuilder().uri(URI.create(dUrl)).GET().build();
						HttpResponse<InputStream> resJar = httpClient.send(reqJar, HttpResponse.BodyHandlers.ofInputStream());
						if (resJar.statusCode() == 200) {
							String fileName = dUrl.substring(dUrl.lastIndexOf('/') + 1);
							if (!fileName.endsWith(".jar") && b.has("symbolicName")) {
								fileName = b.getAsJsonPrimitive("symbolicName").getAsString() + ".jar";
							}
							zos.putNextEntry(new ZipEntry("bundles/" + fileName));
							resJar.body().transferTo(zos);
							zos.closeEntry();
						}
					}
				}
			}
		} catch (Exception e) {
			tempFile.delete();
			throw e;
		}
		return tempFile;
	}

	/**
	 * Check if all OSGi bundles of an extension are installed
	 * @param extension
	 * @return true if all OSGi bundles of an extension are installed
	 */
	public boolean checkAllBundlesInstalled(ExtensionMetadata extension) {
		JsonArray bundles = extension.getBundles();
		if (bundles == null || bundles.size() == 0) return false;
		
		for (JsonElement bel : bundles) {
			String symbolicName = bel.getAsJsonObject().get("symbolicName").getAsString();
			boolean found = false;
			for (Bundle bundle : ExtensionManagerActivator.context.getBundles()) {
				if (symbolicName.equals(bundle.getSymbolicName())) {
					found = true;
					break;
				}
			}
			if (!found) return false;
		}
		return true;
	}

	/**
	 * Create MExtension record
	 * @param extension
	 * @return
	 */
	public MExtension autoRegisterExtension(ExtensionMetadata extension) {
		MExtension mExtension = new MExtension(Env.getCtx(), 0, null);
		mExtension.setExtensionId(extension.getId());
		mExtension.setName(extension.getName());
		mExtension.setExtensionVersion(extension.getVersion());
		mExtension.setExtensionMetadata(extension.toString());
		mExtension.setExtensionState(MExtension.EXTENSIONSTATE_Installed);
		mExtension.saveEx();
		return mExtension;
	}

	/**
	 * Create MExtensionEntity records for an extension from 2Pack installed histories
	 * @param mExtension
	 * @param extension
	 */
	public void syncExtensionEntities(MExtension mExtension, ExtensionMetadata extension) {
		CompletableFuture.runAsync(() -> {
			if (!extension.hasBundles()) return;
			JsonArray bundles = extension.getBundles();
			for (JsonElement bel : bundles) {
				String symbolicName = bel.getAsJsonObject().get("symbolicName").getAsString();
				List<MPackageImp> imps = new Query(Env.getCtx(), MPackageImp.Table_Name, "Name=? AND PK_Status=?", null)
						.setParameters(symbolicName, MPackageImp.PACKAGE_STATUS_COMPLETED)
						.setOnlyActiveRecords(true)
						.list();
				for (MPackageImp imp : imps) {
					List<MPackageImpDetail> details = new Query(Env.getCtx(), MPackageImpDetail.Table_Name, "AD_Package_Imp_ID=? AND Success=? AND Action IN (?,?)", null)
							.setParameters(imp.getAD_Package_Imp_ID(), MPackageImpDetail.ACTION_STATUS_SUCCESS, MPackageImpDetail.ACTION_INSERT, MPackageImpDetail.ACTION_UPDATE)
							.setOnlyActiveRecords(true)
							.list();
					for (MPackageImpDetail detail : details) {
						MExtensionEntity entity = new Query(Env.getCtx(), MExtensionEntity.Table_Name, "AD_Extension_ID=? AND AD_Table_ID=? AND Record_UU=?", null)
								.setParameters(mExtension.getAD_Extension_ID(), detail.getAD_Table_ID(), detail.getRecord_UU())
								.setOnlyActiveRecords(true)
								.first();
						if (entity == null) {
							entity = new MExtensionEntity(Env.getCtx(), 0, null);
							entity.setAD_Extension_ID(mExtension.getAD_Extension_ID());
							entity.setAD_Table_ID(detail.getAD_Table_ID());
							entity.setRecord_UU(detail.getRecord_UU());
							entity.saveEx();
						}
					}
				}
			}
		});
	}

	/**
	 * Prepare MExtension record for installation of extension
	 * @param extension
	 * @return MExtension record with Installing state
	 * @throws Exception
	 */
	public MExtension prepareInstall(ExtensionMetadata extension) throws Exception {
		String id = extension.getId();
		String version = extension.getVersion();

		MExtension mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
				.setParameters(id)
				.setOnlyActiveRecords(true)
				.first();
		if (mExtension != null && MExtension.EXTENSIONSTATE_Installing.equals(mExtension.getExtensionState())) {
			throw new IllegalStateException(Msg.getMsg(Env.getCtx(), "ExtensionAlreadyInstalling")); //Extension is already being installed
		}
		
		// Version check and bundles cleanup
		if (extension.hasBundles()) {
			JsonArray bundles = extension.getBundles();
			for (JsonElement bel : bundles) {
				JsonObject b = bel.getAsJsonObject();
				if (b.has("symbolicName")) {
					String symbolicName = b.get("symbolicName").getAsString();
					Version newVersion = Version.parseVersion(b.has("version") ? b.get("version").getAsString() : "0.0.0");
					for (Bundle bundle : ExtensionManagerActivator.context.getBundles()) {
						if (symbolicName.equals(bundle.getSymbolicName())) {
							Version currentVersion = bundle.getVersion();
							if (currentVersion != null && currentVersion.compareTo(newVersion) > 0) {
								throw new IllegalStateException(Msg.getMsg(Env.getCtx(), "ExtensionBundleNewerVersionInstalled", new Object[] {symbolicName, currentVersion})); //A newer version of bundle {0} ({1}) is already installed
							}
							// If exists and is not newer, uninstall it
							try {
								if (bundle.getState() == Bundle.ACTIVE) {
									bundle.stop();
								}
								bundle.uninstall();
							} catch (Exception e) {
								log.log(Level.WARNING, "Failed to uninstall existing bundle " + symbolicName, e);
							}
						}
					}
				}
			}
		}

		if (mExtension == null) {
			mExtension = new MExtension(Env.getCtx(), 0, null);
			mExtension.setExtensionId(id);
			mExtension.setName(extension.getName());
		}
		mExtension.setExtensionVersion(version);
		mExtension.setExtensionMetadata(extension.toString());
		mExtension.setExtensionState(MExtension.EXTENSIONSTATE_Installing);
		mExtension.saveEx();
		
		return mExtension;
	}

	/**
	 * Update MExtension record to Installed state
	 * @param extension
	 */
	public void handleInstallationSuccess(ExtensionMetadata extension) {
		String extensionId = extension.getId();
		if (extensionId != null) {
			MExtension mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
					.setParameters(extensionId)
					.first();
			if (mExtension != null) {
				mExtension.setExtensionState(MExtension.EXTENSIONSTATE_Installed);
				mExtension.saveEx();
			}
		}
	}

	/**
	 * Update MExtension record to Error state
	 * @param extension
	 */
	public void handleInstallationFailure(ExtensionMetadata extension) {
		String extensionId = extension.getId();
		if (extensionId != null) {
			MExtension mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
					.setParameters(extensionId)
					.first();
			if (mExtension != null) {
				mExtension.setExtensionState(MExtension.EXTENSIONSTATE_Error);
				mExtension.saveEx();
			}
		}
		try {
			uninstallBundles(extension);
		} catch (Exception e) {
			log.log(Level.WARNING, "Failed to cleanup bundles after installation failure", e);
		}
	}
}
