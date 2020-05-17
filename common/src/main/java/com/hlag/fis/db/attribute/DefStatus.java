package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DefStatus implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	I("I"),
	M("M"),
	N("N"),
	U("U");

	String value;

	DefStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DefStatus, String> {
		public Converter() {
			super(DefStatus.class);
		}
	}
}
