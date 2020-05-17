package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TemperatureUnit implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	F("F");

	private String value;

	TemperatureUnit(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<TemperatureUnit, String> {
		public Converter() {
			super(TemperatureUnit.class);
		}
	}
}
