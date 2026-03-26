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
package org.idempiere.extension.manager.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.adempiere.base.annotation.Process;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MExtension;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.idempiere.extension.manager.ExtensionManagerActivator;
import org.idempiere.extension.manager.event.PackageImpDelegate;
import org.idempiere.extension.manager.form.ExtensionBrowserService;
import org.idempiere.extension.manager.form.ExtensionMetadata;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Process
public class InstallExtension extends SvrProcess {
	
	public static final String AD_Process_UU = "019d1fe3-d89d-76d8-960c-0e7f8262003d";

	public InstallExtension() {
	}

	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		MExtension extension = new MExtension(Env.getCtx(), getRecord_ID(), get_TrxName());
		if (extension.getAD_Extension_ID() != getRecord_ID() || getRecord_ID() == 0) {
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "FillMandatory") + Msg.getElement(Env.getCtx(), "AD_Extension_ID"));
		}
		
		HttpClient httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();		
		JsonObject metadata = JsonParser.parseString(extension.getExtensionMetadata()).getAsJsonObject();
		
		// Validate Required Fields
		if (!metadata.has("id") || !metadata.has("version") || !metadata.has("idempiereVersion") 
				|| !metadata.has("name") || !metadata.has("bundles") || !metadata.has("entityType")) {
			throw new AdempiereException("metadata.json is missing required fields (id, name, version, idempiereVersion, bundles, entityType)");
		}
		
		JsonArray bundlesJson = metadata.getAsJsonArray("bundles");
		if (bundlesJson.size() == 0) {
			//No bundles found in metadata.json
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "NoBundlesInExtensionMetadata")); 
		}
		
		// Compatibility Check
		String requiredVersion = metadata.get("idempiereVersion").getAsString();
		Version requiredVersionObj = Version.parseVersion(requiredVersion);		
		String currentVersion = org.compiere.Adempiere.getVersion();
		Version currentVersionObj = Version.parseVersion(currentVersion);
		if (currentVersionObj.compareTo(requiredVersionObj) < 0) {
			//Incompatible iDempiere version. Extension requires {0} and above but current version is {1}"
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "IncompatibleIdempiereVersion", new Object[] { requiredVersion, currentVersion }));
		}
		
		ExtensionBrowserService service = new ExtensionBrowserService();
		String dbError = service.validateDatabaseRequirement(new ExtensionMetadata(metadata));
		if (dbError != null) {
			throw new AdempiereException(dbError);
		}
		
		// Dependency Check
		if (metadata.has("dependencies")) {
			JsonArray dependencies = metadata.getAsJsonArray("dependencies");
			for (JsonElement depEl : dependencies) {
				JsonObject depObj = depEl.getAsJsonObject();
				if (!depObj.has("id") || !depObj.has("version")) {
					continue;
				}
				String depId = depObj.get("id").getAsString();
				String depVersionStr = depObj.get("version").getAsString();
				Version depVersion = Version.parseVersion(depVersionStr);
				
				MExtension depExt = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
						.setParameters(depId)
						.setOnlyActiveRecords(true)
						.first();
				
				if (depExt == null || !MExtension.EXTENSIONSTATE_Installed.equals(depExt.getExtensionState())) {
					//Missing dependency: {0}. Please install it first
					throw new AdempiereException(Msg.getMsg(Env.getCtx(), "MissingDependency", new Object[] {depId}));
				}
				
				Version installedDepVersion = Version.parseVersion(depExt.getExtensionVersion());
				if (installedDepVersion.compareTo(depVersion) < 0) {
					//Incompatible dependency version: {0}. Required {1} and above but installed {2}
					throw new AdempiereException(Msg.getMsg(Env.getCtx(), "IncompatibleDependencyVersion", new Object[] {depId, depVersionStr, depExt.getExtensionVersion()}));
				}
			}
		}
		
		BundleContext context = ExtensionManagerActivator.context;
		if (context == null) {
			throw new AdempiereException(Msg.getMsg(Env.getCtx(), "BundleContextNotFound"));
		}
		
		List<Bundle> installedBundles = new ArrayList<>();
				
		// Download and Install OSGi Bundles
		for (JsonElement el : bundlesJson) {
			JsonObject bundleObj = el.getAsJsonObject();
			if (!bundleObj.has("downloadUrl")) {
				//Bundle {0} is missing downloadUrl
				throw new AdempiereException(Msg.getMsg(Env.getCtx(), "BundleNoDownloadURL", new Object[]{bundleObj.get("symbolicName").getAsString()}));
			}
			String downloadUrl = bundleObj.get("downloadUrl").getAsString();
			
			// convert github blob to raw if needed
			if (downloadUrl.contains("github.com") && downloadUrl.contains("/blob/")) {
				downloadUrl = downloadUrl.replace("github.com", "raw.githubusercontent.com").replace("/blob/", "/");
			}
		
			if (processUI != null)
				processUI.statusUpdate(Msg.getMsg(Env.getCtx(), "DownloadingBundleFrom", new Object[]{bundleObj.get("symbolicName").getAsString(), downloadUrl}));

			addLog("Downloading bundle from " + downloadUrl);
			HttpRequest jarRequest = HttpRequest.newBuilder().uri(URI.create(downloadUrl)).GET().build();
			HttpResponse<InputStream> jarResponse = httpClient.send(jarRequest, HttpResponse.BodyHandlers.ofInputStream());
			
			if (jarResponse.statusCode() != 200) {
				throw new AdempiereException(Msg.getMsg(Env.getCtx(), "HttpFetchFailed", new Object[]{jarResponse.statusCode(), downloadUrl}));
			}
			
			File tempZip = File.createTempFile("extbundle_", ".jar");
			
			try (InputStream is = jarResponse.body(); FileOutputStream fos = new FileOutputStream(tempZip)) {
				is.transferTo(fos);
			}
			
			if (bundleObj.has("sha256")) {
				String expectedSha256 = bundleObj.get("sha256").getAsString();
				String actualSha256 = calculateSHA256(tempZip);
				if (!expectedSha256.equalsIgnoreCase(actualSha256)) {
					tempZip.delete();
					//BundleSHA256HashNotMatch SHA256 hash mismatch for bundle {0}. Expected {1} but got {2}
					throw new AdempiereException(Msg.getMsg(Env.getCtx(), "BundleSHA256HashNotMatch", new Object[]{downloadUrl, expectedSha256, actualSha256}));
				}
			}
			
			if (processUI != null)
				processUI.statusUpdate("Installing bundle from " + downloadUrl);
			addLog("Installing bundle from " + downloadUrl);
			Bundle bundle;
			try (java.io.FileInputStream fis = new java.io.FileInputStream(tempZip)) {
				bundle = context.installBundle(downloadUrl, fis);
			}
			installedBundles.add(bundle);
			
			tempZip.delete();
		}
		
		// Start (Activate) Bundles and Check for 2pack
		String id = metadata.get("id").getAsString();
		for (Bundle bundle : installedBundles) {
			Enumeration<URL> entries = bundle.findEntries("META-INF", "2Pack_*.zip", false);
			if (entries != null && entries.hasMoreElements()) {
				PackageImpDelegate.addExtension(bundle.getSymbolicName(), id);
			}
			if (processUI != null)
				processUI.statusUpdate("Starting bundle " + bundle.getSymbolicName());
			addLog("Starting bundle " + bundle.getSymbolicName());
			bundle.start();			
		}
		
		return "@ExtensionInstallSuccessfully@";
	}
	
	private String calculateSHA256(File file) throws Exception {
		java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
		try (java.io.InputStream is = new java.io.FileInputStream(file)) {
			byte[] buffer = new byte[8192];
			int read;
			while ((read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}
		}
		byte[] hash = digest.digest();
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
