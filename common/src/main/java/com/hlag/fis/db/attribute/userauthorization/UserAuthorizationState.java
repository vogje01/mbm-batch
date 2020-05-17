package com.hlag.fis.db.attribute.userauthorization;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum UserAuthorizationState implements PersistableEnum<String> {
    NONE(" "),
	OK("OK"),
	REJ("REJ"),
	REQ("REQ"),
	REX("REX");
	private String value;

	UserAuthorizationState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<UserAuthorizationState, String> {

		public Converter() {
			super(UserAuthorizationState.class);
		}
	}
}
