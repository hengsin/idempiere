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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.apps.ProcessModalDialog;
import org.adempiere.webui.apps.WProcessCtl;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;
import org.compiere.model.MExtension;
import org.compiere.model.MProcess;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.Util;
import org.idempiere.extension.manager.process.InstallExtension;
import org.idempiere.ui.zk.annotation.Form;

import org.osgi.framework.Version;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Vlayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonParser;

@Form
public class ExtensionBrowserFormController implements IFormController {

	private static final CLogger log = CLogger.getCLogger(ExtensionBrowserFormController.class);

	private ExtensionBrowserForm form;

	private Map<String, ExtensionMetadata> extensionMap;
	private ExtensionMetadata selectedExtension;
	private Div selectedItemComponent;
	private ExtensionBrowserService service;

	public ExtensionBrowserFormController() {
		form = new ExtensionBrowserForm();
		service = new ExtensionBrowserService();
		Selectors.wireEventListeners(form, this);

		
		form.addEventListener("onBuildArchive", ev -> buildArchive((ExtensionMetadata) ev.getData()));
		form.addEventListener("onRunInstall", this::onRunInstall);
		
		loadRepositoryExtensions();
		loadInstalledExtensions();
		form.repositoryTab.focus();
	}

	@Override
	public ADForm getForm() {
		return form;
	}

	private void loadRepositoryExtensions() {
		try {
			extensionMap = new HashMap<>();
			form.extensionListbox.getChildren().clear();
			JsonArray extensions = service.fetchRepositoryExtensions();						
			for (JsonElement el : extensions) {
				ExtensionMetadata extObj = new ExtensionMetadata(el.getAsJsonObject());
				String id = extObj.getId();
				if (Util.isEmpty(id, true))
					continue;
				renderExtensionItem(form.extensionListbox, extObj, true);
				extensionMap.put(id, extObj);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to load repository extensions", e);
			Label emptyLabel = new Label("Error loading extensions: " + e.getMessage());
			emptyLabel.setStyle("padding: 20px; color: #d9534f; display: block; text-align: center;");
			form.extensionListbox.appendChild(emptyLabel);
		}
	}

	private void loadInstalledExtensions() {
		form.installedListbox.getChildren().clear();
		List<MExtension> installed = service.getInstalledExtensions();		
		if (installed.isEmpty()) {
			Label emptyLabel = new Label("No extensions installed");
			emptyLabel.setStyle("padding: 20px; color: #888; display: block; text-align: center;");
			form.installedListbox.appendChild(emptyLabel);
			return;
		}

		for (MExtension ext : installed) {
			String metadataStr = ext.getExtensionMetadata();
			if (!Util.isEmpty(metadataStr, true)) {
				try {
					ExtensionMetadata metadata = new ExtensionMetadata(JsonParser.parseString(metadataStr).getAsJsonObject());
					renderExtensionItem(form.installedListbox, metadata, false);
				} catch (Exception e) {
					log.log(Level.WARNING, "Failed to parse metadata for extension: " + ext.getExtensionId(), e);
				}
			}
		}				
	}

	/**
	 * Render extension item for repository browser or installed extensions
	 * @param container
	 * @param extObj
	 * @param isRepository
	 */
	private void renderExtensionItem(Vlayout container, ExtensionMetadata extObj, boolean isRepository) {
		String name = extObj.getName();
		String description = extObj.hasDescription() ? extObj.getDescription() : "";
		
		Div item = new Div();
		item.setStyle("padding: 15px; border-bottom: 1px solid #eee; cursor: pointer; transition: background 0.2s;");
		
		// Name
		Label nameLabel = new Label(name);
		nameLabel.setStyle("font-weight: 600; font-size: 1.1em; color: #333; display: block;");
		item.appendChild(nameLabel);
		
		// Description
		if (!description.isEmpty()) {
			Label descLabel = new Label(description);
			descLabel.setStyle("display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; margin-top: 5px; color: #666; font-size: 0.9em; line-height: 1.4;");
			item.appendChild(descLabel);
		}
		
		// Categories and Tags
		if (extObj.hasCategories() || extObj.hasTags()) {
			Hlayout tagsLayout = new Hlayout();
			tagsLayout.setStyle("margin-top: 10px; flex-wrap: wrap;");
			
			if (extObj.hasCategories()) {
				for (JsonElement cat : extObj.getCategories()) {
					Label catTag = new Label(cat.getAsString());
					catTag.setStyle("background: #e3f2fd; color: #1976d2; padding: 2px 8px; border-radius: 12px; font-size: 0.75em; margin-right: 6px; border: 1px solid #bbdefb; font-weight: 500;");
					tagsLayout.appendChild(catTag);
				}
			}
			if (extObj.hasTags()) {
				for (JsonElement tag : extObj.getTags()) {
					Label tagTag = new Label(tag.getAsString());
					tagTag.setStyle("background: #f5f5f5; color: #616161; padding: 2px 8px; border-radius: 12px; font-size: 0.75em; margin-right: 6px; border: 1px solid #e0e0e0; font-weight: 500;");
					tagsLayout.appendChild(tagTag);
				}
			}
			item.appendChild(tagsLayout);
		}
		
		item.addEventListener(Events.ON_CLICK, e -> selectExtension(item, extObj));
		container.appendChild(item);
	}

	private void selectExtension(Div item, ExtensionMetadata extension) {
		if (selectedItemComponent != null) {
			selectedItemComponent.setStyle("padding: 15px; border-bottom: 1px solid #eee; cursor: pointer; transition: background 0.2s;");
		}
		
		selectedItemComponent = item;
		selectedExtension = extension;
		
		item.setStyle("padding: 15px 15px 15px 11px; border-bottom: 1px solid #eee; cursor: pointer; transition: background 0.2s; background-color: #f0f7ff; border-left: 4px solid #007bff;");
		
		onSelectExtension();
	}

	/**
	 * On select of extension item in repository extensions or installed extensions pane
	 */
	private void onSelectExtension() {
		if (selectedExtension == null) {
			resetInfoArea();
			return;
		}

		ExtensionMetadata extension = selectedExtension;
		form.infoArea.getChildren().clear();

		// render details of selected extension
		Hlayout mainLayout = new Hlayout();
		mainLayout.setHflex("1");
		mainLayout.setVflex("1");
		mainLayout.setSpacing("0");
		mainLayout.setStyle("overflow: hidden;");

		Tabbox tabbox = new Tabbox();
		tabbox.setHflex("1");
		tabbox.setVflex("1");
		tabbox.setStyle("border:none;");
		
		Tabs tabs = new Tabs();
		Tabpanels panels = new Tabpanels();
		tabbox.appendChild(tabs);
		tabbox.appendChild(panels);

		boolean hasContent = false;
		// Details Tab
		if (extension.hasInfoUrl()) {
			Tab tab = new Tab("Details");
			tabs.appendChild(tab);
			Tabpanel panel = new Tabpanel();
			panel.setStyle("overflow: auto; padding: 20px;");
			panels.appendChild(panel);
			
			String htmlContent = service.fetchAndRenderMarkdown(extension.getInfoUrl());
			Html html = new Html("<article>" + htmlContent + "</article>");
			panel.appendChild(html);
			hasContent = true;
		}

		// Changelog Tab
		if (extension.hasChangeLogUrl()) {
			Tab tab = new Tab("Changelog");
			tabs.appendChild(tab);
			Tabpanel panel = new Tabpanel();
			panel.setStyle("overflow: auto; padding: 20px;");
			panels.appendChild(panel);
			
			String htmlContent = service.fetchAndRenderMarkdown(extension.getChangeLogUrl());
			Html html = new Html("<article>" + htmlContent + "</article>");
			panel.appendChild(html);
			hasContent = true;
		}

		if (hasContent) {
			mainLayout.appendChild(tabbox);
		} else {
			Div empty = new Div();
			empty.setHflex("1");
			mainLayout.appendChild(empty);
		}

		// Sidebar (Aside)
		Div aside = createSidebar(extension);
		mainLayout.appendChild(aside);

		form.infoArea.appendChild(mainLayout);

		updateButtons(extension);
		form.downloadButton.setDisabled(false);
	}

	/**
	 * Reset the details area for selected extension
	 */
	private void resetInfoArea() {
		form.installUpdateButton.setDisabled(true);
		form.installUpdateButton.setLabel("Install");
		form.uninstallButton.setVisible(false);
		form.enableDisableButton.setVisible(false);
		form.downloadButton.setDisabled(true);
		form.registryButton.setDisabled(true);
		form.infoArea.getChildren().clear();
	}

	/**
	 * Create sidebar for extension details
	 * @param extension
	 * @return
	 */
	private Div createSidebar(ExtensionMetadata extension) {
		String id = extension.getId();
		String version = extension.getVersion();
		String lastUpdated = extension.hasReleaseDate() ? extension.getReleaseDate() : "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("<aside style=\"width: 280px; flex-shrink: 0; background-color: #f8f9fa; border-left: 1px solid #e9ecef; padding: 20px; font-family: sans-serif; height: 100%; box-sizing: border-box; overflow-y: auto;\">");
		
		// Installation section
		sb.append("<section style=\"margin-bottom: 25px;\">");
		sb.append("<h3 style=\"margin-top: 0; margin-bottom: 15px; font-size: 1.1em; color: #212529; border-bottom: 2px solid #dee2e6; padding-bottom: 5px;\">Installation</h3>");
		sb.append("<div style=\"margin-bottom: 10px;\"><label style=\"display: block; font-size: 0.85em; color: #6c757d; margin-bottom: 2px;\">ID</label><code style=\"padding: 2px 0px; font-size: 0.9em;\">").append(id).append("</code></div>");
		sb.append("<div style=\"margin-bottom: 10px;\"><label style=\"display: block; font-size: 0.85em; color: #6c757d; margin-bottom: 2px;\">Version</label><span style=\"font-weight: 500;\">").append(version).append("</span></div>");
		sb.append("<div><label style=\"display: block; font-size: 0.85em; color: #6c757d; margin-bottom: 2px;\">Last Updated</label><span>").append(lastUpdated).append("</span></div>");
		sb.append("</section>");
		
		// Categories section
		if (extension.hasCategories()) {
			sb.append("<section style=\"margin-bottom: 25px;\">");
			sb.append("<h3 style=\"margin-bottom: 15px; font-size: 1.1em; color: #212529; border-bottom: 2px solid #dee2e6; padding-bottom: 5px;\">Categories</h3>");
			sb.append("<ul style=\"list-style: none; padding: 0; margin: 0;\">");
			JsonArray categories = extension.getCategories();
			for (JsonElement cat : categories) {
				sb.append("<li style=\"margin-bottom: 6px; display: flex; align-items: center;\"><span style=\"width: 6px; height: 6px; background: #007bff; border-radius: 50%; margin-right: 10px;\"></span>").append(cat.getAsString()).append("</li>");
			}
			sb.append("</ul></section>");
		}
		
		// Tags section
		if (extension.hasTags()) {
			sb.append("<section>");
			sb.append("<h3 style=\"margin-bottom: 15px; font-size: 1.1em; color: #212529; border-bottom: 2px solid #dee2e6; padding-bottom: 5px;\">Tags</h3>");
			sb.append("<div style=\"display: flex; flex-wrap: wrap; gap: 6px;\">");
			JsonArray tags = extension.getTags();
			for (JsonElement tag : tags) {
				sb.append("<span style=\"background: #fff; color: #495057; border: 1px solid #ced4da; padding: 2px 10px; border-radius: 15px; font-size: 0.8em; font-weight: 500;\">")
				  .append(tag.getAsString()).append("</span>");
			}
			sb.append("</div></section>");
		}
		
		sb.append("</aside>");
		
		Div asideDiv = new Div();
		asideDiv.setVflex("1");
		Html html = new Html(sb.toString());
		asideDiv.appendChild(html);
		return asideDiv;
	}

	/**
	 * Update button state and label
	 * @param extension
	 */
	private void updateButtons(ExtensionMetadata extension) {
		String extensionId = extension.getId();
		MExtension mExtension = null;
		if (extensionId != null) {
			mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
					.setParameters(extensionId)
					.setOnlyActiveRecords(true)
					.first();
		}

		// not exists or not install
		if (mExtension == null || (!MExtension.EXTENSIONSTATE_Installed.equals(mExtension.getExtensionState()) 
			&& !MExtension.EXTENSIONSTATE_Disabled.equals(mExtension.getExtensionState()))) {
			if (service.checkAllBundlesInstalled(extension)) {
				mExtension = service.autoRegisterExtension(extension);
				if (mExtension != null) {
					service.syncExtensionEntities(mExtension, extension);
				}
			} else {
				mExtension = null;
			}
		}

		if (mExtension == null || MExtension.EXTENSIONSTATE_Uninstalled.equals(mExtension.getExtensionState())) {
			form.installUpdateButton.setLabel("Install");
			form.installUpdateButton.setVisible(true);
			form.installUpdateButton.setDisabled(false);
			form.uninstallButton.setVisible(false);
			form.enableDisableButton.setVisible(false);
		} else {
			String installedVersionStr = mExtension.getExtensionVersion();
			Version installedVersion = Version.parseVersion(installedVersionStr != null ? installedVersionStr : "0.0.0");
			Version latestVersion = Version.parseVersion(extension.getVersion());
			
			if (installedVersion.compareTo(latestVersion) < 0) {
				form.installUpdateButton.setLabel("Update");
				form.installUpdateButton.setVisible(true);
			} else {
				form.installUpdateButton.setVisible(false);
			}
			form.installUpdateButton.setDisabled(false);
			
			form.uninstallButton.setVisible(true);
			form.enableDisableButton.setVisible(true);
			
			// Handle isBundled requirement
			if (mExtension.isBundled()) {
				form.uninstallButton.setDisabled(true);
				form.uninstallButton.setTooltiptext("Bundled extensions cannot be uninstalled");
			} else {
				form.uninstallButton.setDisabled(false);
				form.uninstallButton.setTooltiptext("");
			}
			
			if (MExtension.EXTENSIONSTATE_Disabled.equals(mExtension.getExtensionState())) {
				form.enableDisableButton.setLabel("Enable");
			} else {
				form.enableDisableButton.setLabel("Disable");
			}
		}
		
		form.registryButton.setDisabled(mExtension == null);
	}

	@Listen("onSelect=#repositoryTab")
	public void onSelectRepositoryTab() {
		resetInfoArea();
		loadRepositoryExtensions();
	}

	@Listen("onSelect=#installedTab")
	public void onSelectInstalledTab() {
		resetInfoArea();
		loadInstalledExtensions();		
	}

	@Listen("onClick=#installUpdateButton")
	public void onInstallUpdate() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;
		String label = form.installUpdateButton.getLabel();
		
		if ("Install".equals(label)) {
			onInstall();
		} else if ("Update".equals(label)) {
			onUpdate(extension);
		}
	}

	@Listen("onClick=#uninstallButton")
	public void onUninstall() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;
		try {
			service.uninstallExtension(extension);
			
			Clients.showNotification("Extension uninstalled", "info", form.infoArea, "top_left", 5000);
			loadInstalledExtensions();
			updateButtons(extension);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Uninstall failed", e);
			Clients.showNotification("Uninstall failed: " + e.getMessage(), "error", form.infoArea, "top_left", 5000);
		}
	}



	@Listen("onClick=#enableDisableButton")
	public void onEnableDisable() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;
		String label = form.enableDisableButton.getLabel();
		if ("Disable".equals(label)) {
			onDisable(extension);
		} else if ("Enable".equals(label)) {
			onEnable(extension);
		}
	}

	/**
	 * Update extension
	 * @param extension
	 */
	private void onUpdate(ExtensionMetadata extension) {
		try {
			service.uninstallBundles(extension);
			onInstall();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Update cleanup failed", e);
			Clients.showNotification("Update initialization failed: " + e.getMessage(), "error", form.infoArea, "top_left", 5000);
		}
	}

	/**
	 * Install currently selected extension
	 */
	private void onInstall() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;

		try {
			MExtension mExtension = service.prepareInstall(extension);
			MProcess process = MProcess.get(InstallExtension.AD_Process_UU);
			if (process == null)
				throw new IllegalStateException("InstallExtension process not found");
			ProcessInfo pi = new ProcessInfo(process.getName(), process.getAD_Process_ID());
			pi.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
			pi.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			pi.setTable_ID(mExtension.get_Table_ID());
			pi.setRecord_ID(mExtension.getAD_Extension_ID());
			
			Clients.showBusy(form, "Initializing installation...");
			Events.echoEvent("onRunInstall", form, pi);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to initialize installation", e);
			Clients.showNotification("Error: " + e.getMessage(), "error", form.infoArea, "top_left", 5000);
		}
	}

	/**
	 * Event handler to run InstallExtension process
	 * @param ev
	 */
	public void onRunInstall(Event ev) {
		ProcessInfo pi = (ProcessInfo) ev.getData();
		try {
			Clients.clearBusy();
			WProcessCtl.process(getForm().getWindowNo(), pi, (Trx)null, e -> {
				if (ProcessModalDialog.ON_WINDOW_CLOSE.equals(e.getName())){
					if (!pi.isError()) {
						if (selectedExtension != null) {
							service.handleInstallationSuccess(selectedExtension);
							updateButtons(selectedExtension);
						}
						loadInstalledExtensions();
						Clients.showNotification(pi.getSummary(), "info", form.infoArea, "top_left", 5000);
					} else {
						handleInstallationFailure(pi.getSummary());
					}
				}
			});			
		} catch (Exception e) {
			log.log(Level.SEVERE, "Installation failed", e);
			handleInstallationFailure(e.getMessage());
		} finally {
			Clients.clearBusy(form);
		}
	}

	private void handleInstallationFailure(String summary) {
		if (selectedExtension != null) {
			service.handleInstallationFailure(selectedExtension);
			loadInstalledExtensions();
			updateButtons(selectedExtension);
		}
		Clients.showNotification("Installation failed: " + summary, "error", form.infoArea, "top_left", 5000);
	}

	/**
	 * Disable extension
	 * @param extension
	 */
	private void onDisable(ExtensionMetadata extension) {
		try {
			service.disableExtension(extension);
			Clients.showNotification("Extension disabled", "info", form.infoArea, "top_left", 5000);
			loadInstalledExtensions();
			updateButtons(extension);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Disable failed", e);
			Clients.showNotification("Disable failed: " + e.getMessage(), "error", form.infoArea, "top_left", 5000);
		}
	}

	/**
	 * Enable extension
	 * @param extension
	 */
	private void onEnable(ExtensionMetadata extension) {
		try {
			service.enableExtension(extension);
			Clients.showNotification("Extension enabled", "info", form.infoArea, "top_left", 5000);
			loadInstalledExtensions();
			updateButtons(extension);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Enable failed", e);
			Clients.showNotification("Enable failed: " + e.getMessage(), "error", form.infoArea, "top_left", 5000);
		}
	}

	@Listen("onClick=#downloadButton")
	public void onDownload() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;
		Clients.showBusy(form, "Downloading .idext archive...");
		Events.echoEvent("onBuildArchive", form, extension);
	}

	/**
	 * Create extension archive (.idext)
	 * @param extension
	 */
	private void buildArchive(ExtensionMetadata extension) {
		try {
			File archiveFile = service.buildArchive(extension);
			String idextName = extension.getId() + ".idext";
			AMedia media = new AMedia(idextName, null, "application/zip", archiveFile, true);
			Filedownload.save(media);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to build archive", e);
			Clients.showNotification("Failed to build archive: " + e.getMessage(), "error", form.infoArea, "top_left", 5000);
		} finally {
			Clients.clearBusy(form);
		}
	}

	@Listen("onClick=#registryButton")
	public void onRegistry() {
		if (selectedExtension == null) return;
		String extensionId = selectedExtension.getId();
		if (extensionId != null) {
			MExtension mExtension = new Query(Env.getCtx(), MExtension.Table_Name, "ExtensionId=?", null)
					.setParameters(extensionId)
					.setOnlyActiveRecords(true)
					.first();
			if (mExtension != null) {
				AEnv.zoom(mExtension.get_Table_ID(), mExtension.getAD_Extension_ID(), null);
			}
		}
	}
}
