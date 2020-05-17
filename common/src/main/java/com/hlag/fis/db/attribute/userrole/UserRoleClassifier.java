package com.hlag.fis.db.attribute.userrole;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum UserRoleClassifier implements PersistableEnum<String> {
    NONE(" "),
	B("B"),
	R("R"),
	U("U"),
	W("W");

	String value;

	UserRoleClassifier(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<UserRoleClassifier, String> {
		public Converter() {
			super(UserRoleClassifier.class);
		}
	}
}
