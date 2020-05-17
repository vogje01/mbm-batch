package com.hlag.fis.db.attribute.organizationplace;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum OrganizationPlaceValidStateTransition implements PersistableEnum<String> {
	NONE(" "),
	D("D"),
	H("H"),
	I("I"),
	X("X");

	private String value;

	OrganizationPlaceValidStateTransition(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<OrganizationPlaceValidStateTransition, String> {
		public Converter() {
			super(OrganizationPlaceValidStateTransition.class);
		}
	}
}