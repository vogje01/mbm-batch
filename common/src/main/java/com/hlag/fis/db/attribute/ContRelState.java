package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ContRelState implements PersistableEnum<String> {
    NONE(" "),
	P("P"),
	S("S"),;

	String value;

	ContRelState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ContRelState, String> {
		public Converter() {
			super(ContRelState.class);
		}
	}
}
