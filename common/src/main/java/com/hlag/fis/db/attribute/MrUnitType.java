package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MrUnitType implements PersistableEnum<String> {
    NONE(" "),
	F("F"),
	H("H"),
	M("M"),
	O("O"),
	R("R"),
	U("U");

	private String value;

	MrUnitType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MrUnitType, String> {
		public Converter() {
			super(MrUnitType.class);
		}
	}
}
