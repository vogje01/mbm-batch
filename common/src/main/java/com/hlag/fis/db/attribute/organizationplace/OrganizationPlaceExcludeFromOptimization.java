package com.hlag.fis.db.attribute.organizationplace;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum OrganizationPlaceExcludeFromOptimization implements PersistableEnum<String> {
    NONE(" "),
	F("F"),
	N("N"),
	O("O"),
	U("U");

	private String value;

	OrganizationPlaceExcludeFromOptimization(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<OrganizationPlaceExcludeFromOptimization, String> {
		public Converter() {
			super(OrganizationPlaceExcludeFromOptimization.class);
		}
	}
}