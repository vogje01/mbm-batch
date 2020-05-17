package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DateOrigin implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	C("C"),
	D("D"),
	E("E"),
	P("P"),
	S("S");

	private String value;

	DateOrigin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DateOrigin, String> {
		public Converter() {
			super(DateOrigin.class);
		}
	}
}
