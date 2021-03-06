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

package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;

/**
 * @author Zoltán Takács
 */
public class CommerceAddressWebSiteCreatorForm {

	public static Form<CommerceAddressWebSiteCreatorForm> buildForm(
		Form.Builder<CommerceAddressWebSiteCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The address creator form"
		).description(
			__ -> "This form can be used to create an address"
		).constructor(
			CommerceAddressWebSiteCreatorForm::new
		).addOptionalBoolean(
			"defaultBilling",
			CommerceAddressWebSiteCreatorForm::setDefaultBilling
		).addOptionalBoolean(
			"defaultShipping",
			CommerceAddressWebSiteCreatorForm::setDefaultShipping
		).addOptionalDouble(
			"longitude", CommerceAddressWebSiteCreatorForm::setLongitude
		).addOptionalDouble(
			"latitude", CommerceAddressWebSiteCreatorForm::setLatitude
		).addOptionalLong(
			"regionId", CommerceAddressWebSiteCreatorForm::setRegionId
		).addOptionalString(
			"description", CommerceAddressWebSiteCreatorForm::setDescription
		).addOptionalString(
			"phoneNumber", CommerceAddressWebSiteCreatorForm::setPhoneNumber
		).addOptionalString(
			"street2", CommerceAddressWebSiteCreatorForm::setStreet2
		).addOptionalString(
			"street3", CommerceAddressWebSiteCreatorForm::setStreet3
		).addOptionalString(
			"zip", CommerceAddressWebSiteCreatorForm::setZip
		).addRequiredString(
			"accountExternalReferenceCode",
			CommerceAddressWebSiteCreatorForm::setAccountExternalReferenceCode
		).addRequiredString(
			"city", CommerceAddressWebSiteCreatorForm::setCity
		).addRequiredString(
			"countryTwoLettersISOCode",
			CommerceAddressWebSiteCreatorForm::setCountryTwoLettersISOCode
		).addRequiredString(
			"name", CommerceAddressWebSiteCreatorForm::setName
		).addRequiredString(
			"street1", CommerceAddressWebSiteCreatorForm::setStreet1
		).build();
	}

	public String getAccountExternalReferenceCode() {
		return _accountExternalReferenceCode;
	}

	public String getCity() {
		return _city;
	}

	public String getCountryTwoLettersISOCode() {
		return _countryTwoLettersISOCode;
	}

	public boolean getDefaultBilling() {
		return _defaultBilling;
	}

	public boolean getDefaultShipping() {
		return _defaultShipping;
	}

	public String getDescription() {
		return _description;
	}

	public double getLatitude() {
		return _latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	public String getName() {
		return _name;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public long getRegionId() {
		return _regionId;
	}

	public String getStreet1() {
		return _street1;
	}

	public String getStreet2() {
		return _street2;
	}

	public String getStreet3() {
		return _street3;
	}

	public String getZip() {
		return _zip;
	}

	public void setAccountExternalReferenceCode(
		String accountExternalReferenceCode) {

		_accountExternalReferenceCode = accountExternalReferenceCode;
	}

	public void setCity(String city) {
		_city = city;
	}

	public void setCountryTwoLettersISOCode(String countryTwoLettersISOCode) {
		_countryTwoLettersISOCode = countryTwoLettersISOCode;
	}

	public void setDefaultBilling(boolean defaultBilling) {
		_defaultBilling = defaultBilling;
	}

	public void setDefaultShipping(boolean defaultShipping) {
		_defaultShipping = defaultShipping;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setLatitude(double latitude) {
		_latitude = latitude;
	}

	public void setLongitude(double longitude) {
		_longitude = longitude;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	public void setStreet1(String street1) {
		_street1 = street1;
	}

	public void setStreet2(String street2) {
		_street2 = street2;
	}

	public void setStreet3(String street3) {
		_street3 = street3;
	}

	public void setZip(String zip) {
		_zip = zip;
	}

	private String _accountExternalReferenceCode;
	private String _city;
	private String _countryTwoLettersISOCode;
	private boolean _defaultBilling;
	private boolean _defaultShipping;
	private String _description;
	private double _latitude;
	private double _longitude;
	private String _name;
	private String _phoneNumber;
	private long _regionId;
	private String _street1;
	private String _street2;
	private String _street3;
	private String _zip;

}