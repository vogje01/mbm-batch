package com.hlag.fis.db.attribute.users;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum Sex implements PersistableEnum<String> {
    NONE(" "),
	F("F"),
	M("M");

	String value;

	Sex(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<Sex, String> {
		public Converter() {
			super(Sex.class);
		}
	}
}
