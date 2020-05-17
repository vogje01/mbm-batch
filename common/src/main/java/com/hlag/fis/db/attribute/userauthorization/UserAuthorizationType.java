package com.hlag.fis.db.attribute.userauthorization;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum UserAuthorizationType implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	M("M");
	private String value;

	UserAuthorizationType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<UserAuthorizationType, String> {

		public Converter() {
			super(UserAuthorizationType.class);
		}
	}
}
