package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum FreeTimeRemState implements PersistableEnum<String> {
	NONE(""),
	E("E"),
	P("P"),
	S("S"),;

	String value;

	FreeTimeRemState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<FreeTimeRemState, String> {
		public Converter() {
			super(FreeTimeRemState.class);
		}
	}
}
