package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ReadyForTransfer implements PersistableEnum<String> {
    NONE(" "),
	Y("Y"),
	N("N"),
	R("R");

	private String value;

	ReadyForTransfer(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ReadyForTransfer, String> {
		public Converter() {
			super(ReadyForTransfer.class);
		}
	}
}
