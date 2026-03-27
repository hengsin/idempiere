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
import org.compiere.util.Msg;
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
	private List<ExtensionMetadata> allRepositoryExtensions;
	private List<ExtensionMetadata> allInstalledExtensions;

	public ExtensionBrowserFormController() {
		form = new ExtensionBrowserForm();
		service = new ExtensionBrowserService();
		Selectors.wireEventListeners(form, this);
		
		form.addEventListener("onBuildArchive", ev -> buildArchive((ExtensionMetadata) ev.getData()));
		form.addEventListener("onRunInstall", this::onRunInstall);
		
		loadRepositoryExtensions();
		loadInstalledExtensions();
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
			allRepositoryExtensions = new java.util.ArrayList<>(extensionMap.values());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to load repository extensions", e);
			Label emptyLabel = new Label(Msg.getMsg(Env.getCtx(), "ErrorLoadingExtensions", new Object[]{e.getMessage()})); //Error loading extensions: {0}
			emptyLabel.setSclass("error");
			form.extensionListbox.appendChild(emptyLabel);
		}
	}

	private void loadInstalledExtensions() {
		form.installedListbox.getChildren().clear();
		List<MExtension> installed = service.getInstalledExtensions();		
		if (installed.isEmpty()) {
			Label emptyLabel = new Label(Msg.getMsg(Env.getCtx(), "NoExtensionsInstalled")); //No extensions installed
			emptyLabel.setSclass("info");
			form.installedListbox.appendChild(emptyLabel);
			return;
		}

		allInstalledExtensions = new java.util.ArrayList<>();
		for (MExtension ext : installed) {
			String metadataStr = ext.getExtensionMetadata();
			if (!Util.isEmpty(metadataStr, true)) {
				try {
					ExtensionMetadata metadata = new ExtensionMetadata(JsonParser.parseString(metadataStr).getAsJsonObject());
					allInstalledExtensions.add(metadata);
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
		item.setSclass("item");
		
		// Name
		Label nameLabel = new Label(name);
		nameLabel.setSclass("name");
		item.appendChild(nameLabel);
		
		// Description
		if (!description.isEmpty()) {
			Label descLabel = new Label(description);
			descLabel.setSclass("description");
			item.appendChild(descLabel);
		}
		
		// Categories and Tags
		if (extObj.hasCategories() || extObj.hasTags()) {
			Hlayout tagsLayout = new Hlayout();
			tagsLayout.setStyle("margin-top: 10px; flex-wrap: wrap;");
			
			if (extObj.hasCategories()) {
				for (JsonElement cat : extObj.getCategories()) {
					Label catTag = new Label(cat.getAsString());
					catTag.setSclass("category");
					tagsLayout.appendChild(catTag);
				}
			}
			if (extObj.hasTags()) {
				for (JsonElement tag : extObj.getTags()) {
					Label tagTag = new Label(tag.getAsString());
					tagTag.setSclass("tag");
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
			selectedItemComponent.setSclass("item");
		}
		
		selectedItemComponent = item;
		selectedExtension = extension;
		
		item.setSclass("item selected");
		
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
			Tab tab = new Tab(Msg.getMsg(Env.getCtx(), "ExtensionDetails")); //Details
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
			Tab tab = new Tab(Msg.getMsg(Env.getCtx(), "ExtensionChangeLog")); //Changelog
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
		form.installUpdateButton.setLabel(Msg.getMsg(Env.getCtx(), "Install"));
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
		sb.append("<aside class=\"sidebar\">");
		
		// Installation section
		sb.append("<section>");
		sb.append("<h3>Installation</h3>");
		sb.append("<div><label>ID</label><code>").append(id).append("</code></div>");
		sb.append("<div><label>%s</label><span>".formatted(Msg.getMsg(Env.getCtx(), "Version"))).append(version).append("</span></div>");
		sb.append("<div><label>%s</label><span>".formatted(Msg.getMsg(Env.getCtx(), "Updated"))).append(lastUpdated).append("</span></div>");
		sb.append("</section>");
		
		// Categories section
		if (extension.hasCategories()) {
			sb.append("<section>");
			sb.append("<h3>%s</h3>".formatted(Msg.getMsg(Env.getCtx(), "ExtensionCategories"))); //Categories
			sb.append("<ul>");
			JsonArray categories = extension.getCategories();
			for (JsonElement cat : categories) {
				sb.append("<li><span></span>").append(cat.getAsString()).append("</li>");
			}
			sb.append("</ul></section>");
		}
		
		// Tags section
		if (extension.hasTags()) {
			sb.append("<section>");
			sb.append("<h3>%s</h3>".formatted(Msg.getMsg(Env.getCtx(), "ExtensionTags"))); //Tags
			sb.append("<div class=\"tags\">");
			JsonArray tags = extension.getTags();
			for (JsonElement tag : tags) {
				sb.append("<span class=\"tag\">")
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
			form.installUpdateButton.setLabel(Msg.getMsg(Env.getCtx(), "Install"));
			form.installUpdateButton.setVisible(true);
			form.installUpdateButton.setDisabled(false);
			form.uninstallButton.setVisible(false);
			form.enableDisableButton.setVisible(false);
		} else {
			String installedVersionStr = mExtension.getExtensionVersion();
			Version installedVersion = Version.parseVersion(installedVersionStr != null ? installedVersionStr : "0.0.0");
			Version latestVersion = Version.parseVersion(extension.getVersion());
			
			if (installedVersion.compareTo(latestVersion) < 0) {
				form.installUpdateButton.setLabel(Msg.getMsg(Env.getCtx(), "Update"));
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
				form.uninstallButton.setTooltiptext(Msg.getMsg(Env.getCtx(), "CannotUninstallBundledExtension")); //Bundled extensions cannot be uninstalled
			} else {
				form.uninstallButton.setDisabled(false);
				form.uninstallButton.setTooltiptext("");
			}
			
			if (MExtension.EXTENSIONSTATE_Disabled.equals(mExtension.getExtensionState())) {
				form.enableDisableButton.setLabel(Msg.getMsg(Env.getCtx(), "Enable")); //Enable
			} else {
				form.enableDisableButton.setLabel(Msg.getMsg(Env.getCtx(), "Disable")); //Disable
			}
		}
		
		form.registryButton.setDisabled(mExtension == null);
	}

	@Listen("onSelect=#repositoryTab")
	public void onSelectRepositoryTab() {
		resetInfoArea();
		loadRepositoryExtensions();
		if (!form.filterInput.getValue().isEmpty()) {
			onFilter();
		}
	}

	@Listen("onSelect=#installedTab")
	public void onSelectInstalledTab() {
		resetInfoArea();
		loadInstalledExtensions();
		if (!form.filterInput.getValue().isEmpty()) {
			onFilter();
		}
	}

	@Listen("onOK=#filterInput; onClick=#filterButton")
	public void onFilter() {
		String filter = form.filterInput.getValue().trim();
		Vlayout container = form.repositoryTab.isSelected() ? form.extensionListbox : form.installedListbox;
		List<ExtensionMetadata> allList = form.repositoryTab.isSelected() ? allRepositoryExtensions : allInstalledExtensions;
		
		if (allList == null) return;
		
		container.getChildren().clear();
		if (filter.isEmpty()) {
			for (ExtensionMetadata ext : allList) {
				renderExtensionItem(container, ext, form.repositoryTab.isSelected());
			}
			return;
		}
		
		String filterLower = filter.toLowerCase();
		boolean filterTag = filterLower.startsWith("tag:");
		boolean filterCategory = filterLower.startsWith("category:");
		String searchStr = filterLower;
		if (filterTag) searchStr = filterLower.substring(4).trim();
		else if (filterCategory) searchStr = filterLower.substring(9).trim();
		
		for (ExtensionMetadata ext : allList) {
			boolean match = false;
			if (filterTag) {
				if (ext.hasTags()) {
					for (JsonElement tag : ext.getTags()) {
						if (tag.getAsString().toLowerCase().contains(searchStr)) {
							match = true;
							break;
						}
					}
				}
			} else if (filterCategory) {
				if (ext.hasCategories()) {
					for (JsonElement cat : ext.getCategories()) {
						if (cat.getAsString().toLowerCase().contains(searchStr)) {
							match = true;
							break;
						}
					}
				}
			} else {
				if (ext.getName() != null && ext.getName().toLowerCase().contains(searchStr)) {
					match = true;
				} else if (ext.getDescription() != null && ext.getDescription().toLowerCase().contains(searchStr)) {
					match = true;
				}
			}
			
			if (match) {
				renderExtensionItem(container, ext, form.repositoryTab.isSelected());
			}
		}
		
		if (container.getChildren().isEmpty()) {
			Label emptyLabel = new Label(Msg.getMsg(Env.getCtx(), "NoMatchingExtensions")); //No matching extensions found
			emptyLabel.setSclass("info");
			container.appendChild(emptyLabel);
		}
	}

	@Listen("onClick=#installUpdateButton")
	public void onInstallUpdate() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;
		String label = form.installUpdateButton.getLabel();
		
		if (Msg.getMsg(Env.getCtx(), "Install").equals(label)) {
			onInstall();
		} else if (Msg.getMsg(Env.getCtx(), "Update").equals(label)) {
			onUpdate(extension);
		}
	}

	@Listen("onClick=#uninstallButton")
	public void onUninstall() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;
		try {
			service.uninstallExtension(extension);
			
			showNotification("Extension uninstalled", "info");
			loadInstalledExtensions();
			updateButtons(extension);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Uninstall failed", e);
			showNotification("Uninstall failed: " + e.getMessage(), "error");
		}
	}

	private void showNotification(String message, String type) {
		Clients.showNotification(message, type, form.infoArea, "top_left", 5000);
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
			showNotification("Update initialization failed: " + e.getMessage(), "error");
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
			
			Clients.showBusy(form, Msg.getMsg(Env.getCtx(), "Processing"));
			Events.echoEvent("onRunInstall", form, pi);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to initialize installation", e);
			showNotification("Error: " + e.getMessage(), "error");
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
						showNotification(pi.getSummary(), "info");
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
		showNotification("Installation failed: " + summary, "error");
	}

	/**
	 * Disable extension
	 * @param extension
	 */
	private void onDisable(ExtensionMetadata extension) {
		try {
			service.disableExtension(extension);
			showNotification("Extension disabled", "info");
			loadInstalledExtensions();
			updateButtons(extension);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Disable failed", e);
			showNotification("Disable failed: " + e.getMessage(), "error");
		}
	}

	/**
	 * Enable extension
	 * @param extension
	 */
	private void onEnable(ExtensionMetadata extension) {
		try {
			service.enableExtension(extension);
			showNotification("Extension enabled", "info");
			loadInstalledExtensions();
			updateButtons(extension);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Enable failed", e);
			showNotification("Enable failed: " + e.getMessage(), "error");
		}
	}

	@Listen("onClick=#downloadButton")
	public void onDownload() {
		if (selectedExtension == null) return;
		
		ExtensionMetadata extension = selectedExtension;
		Clients.showBusy(form, Msg.getMsg(Env.getCtx(), "Processing"));
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
			showNotification("Failed to build archive: " + e.getMessage(), "error");
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
