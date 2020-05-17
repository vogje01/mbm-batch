package com.hlag.fis.db.attribute.organizationplace;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum OrganizationPlaceAddressType implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	O("O"),
	S("S");

	private String value;

	OrganizationPlaceAddressType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<OrganizationPlaceAddressType, String> {
		public Converter() {
			super(OrganizationPlaceAddressType.class);
		}
	}
}