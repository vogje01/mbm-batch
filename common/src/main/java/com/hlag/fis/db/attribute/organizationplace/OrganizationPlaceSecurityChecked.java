package com.hlag.fis.db.attribute.organizationplace;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum OrganizationPlaceSecurityChecked implements PersistableEnum<String> {
    NONE(" "),
	F("F"),
	N("N"),
	P("P");

	private String value;

	OrganizationPlaceSecurityChecked(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<OrganizationPlaceSecurityChecked, String> {
		public Converter() {
			super(OrganizationPlaceSecurityChecked.class);
		}
	}
}