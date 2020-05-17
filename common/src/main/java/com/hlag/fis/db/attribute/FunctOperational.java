package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum FunctOperational implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	D("D"),
	E("E"),
	M("M"),
	O("O"),
	S("S"),
	T("T");

	String value;

	FunctOperational(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<FunctOperational, String> {
		public Converter() {
			super(FunctOperational.class);
		}
	}
}
