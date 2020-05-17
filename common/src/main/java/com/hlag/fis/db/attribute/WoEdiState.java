package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum WoEdiState implements PersistableEnum<String> {
    NONE(" "),
	S("S"),
	W("W");

	String value;

	WoEdiState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<WoEdiState, String> {
		public Converter() {
			super(WoEdiState.class);
		}
	}
}
