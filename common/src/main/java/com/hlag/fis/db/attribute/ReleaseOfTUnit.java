package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ReleaseOfTUnit implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	M("M"),
	P("P"),
	R("R"),
	S("S"),
	W("W");

	private String value;

	ReleaseOfTUnit(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ReleaseOfTUnit, String> {
		public Converter() {
			super(ReleaseOfTUnit.class);
		}
	}
}
