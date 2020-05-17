package com.hlag.fis.db.attribute.organizationplace;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum OrganizationPlaceExternalIndicator implements PersistableEnum<String> {
    NONE(" "),
	E("E"),
	I("I");

	private String value;

	OrganizationPlaceExternalIndicator(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<OrganizationPlaceExternalIndicator, String> {
		public Converter() {
			super(OrganizationPlaceExternalIndicator.class);
		}
	}
}