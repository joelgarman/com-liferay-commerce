/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.initializer.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.io.File;
import java.io.InputStream;

import java.util.Iterator;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = PortletSettingsImporter.class)
public class PortletSettingsImporter {

	public void importPortletSettings(
			JSONArray jsonArray, ClassLoader classLoader,
			String displayTemplateDependenciesPath,
			ServiceContext serviceContext)
		throws Exception {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String portletName = jsonObject.getString("portletName");

			_importPortletSettings(
				jsonObject, portletName, classLoader,
				displayTemplateDependenciesPath, serviceContext);
		}
	}

	private String _importDisplayTemplate(
			JSONObject jsonObject, ClassLoader classLoader,
			String displayTemplateDependenciesPath,
			ServiceContext serviceContext)
		throws Exception {

		String fileName = jsonObject.getString("FileName");
		String name = jsonObject.getString("Name");
		String portletClassName = jsonObject.getString("PortletClassName");

		long classNameId = _portal.getClassNameId(portletClassName);

		long resourceClassNameId = _portal.getClassNameId(
			PortletDisplayTemplate.class);

		InputStream inputStream = classLoader.getResourceAsStream(
			displayTemplateDependenciesPath + fileName);

		File file = FileUtil.createTempFile(inputStream);

		DDMTemplate ddmTemplate = _cpFileImporter.getDDMTemplate(
			file, classNameId, 0L, resourceClassNameId, name,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
			TemplateConstants.LANG_TYPE_FTL, serviceContext);

		return "ddmTemplate_" + ddmTemplate.getTemplateKey();
	}

	private void _importPortletSettings(
			JSONObject jsonObject, String portletName, ClassLoader classLoader,
			String displayTemplateDependenciesPath,
			ServiceContext serviceContext)
		throws Exception {

		long groupId = serviceContext.getScopeGroupId();

		String instanceId = jsonObject.getString("instanceId");
		String layoutFriendlyURL = jsonObject.getString("layoutFriendlyURL");

		JSONObject portletPreferencesJSONObject = jsonObject.getJSONObject(
			"portletPreferences");

		String portletId = PortletIdCodec.encode(portletName, instanceId);

		PortletPreferences portletSetup = null;

		if (Validator.isNotNull(layoutFriendlyURL)) {
			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				groupId, false, layoutFriendlyURL);

			if (layout == null) {
				layout = _layoutLocalService.fetchLayoutByFriendlyURL(
					groupId, true, layoutFriendlyURL);
			}

			if (layout != null) {
				portletSetup =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);
			}
			else {
				portletSetup =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						serviceContext.getCompanyId(), groupId,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
						LayoutConstants.DEFAULT_PLID, portletId,
						StringPool.BLANK);
			}
		}

		Iterator<String> iterator = portletPreferencesJSONObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			String value = null;

			if (key.equals("assetVocabularyId")) {
				value = portletPreferencesJSONObject.getString(key);

				AssetVocabulary assetVocabulary =
					_assetVocabularyLocalService.getGroupVocabulary(
						groupId, value);

				value = String.valueOf(assetVocabulary.getVocabularyId());
			}
			else if (key.equals("displayStyle")) {
				value = _importDisplayTemplate(
					portletPreferencesJSONObject.getJSONObject(key),
					classLoader, displayTemplateDependenciesPath,
					serviceContext);
			}
			else if (key.equals("displayStyleGroupId")) {
				value = String.valueOf(groupId);
			}
			else {
				value = portletPreferencesJSONObject.getString(key);
			}

			portletSetup.setValue(key, value);
		}

		portletSetup.store();
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}