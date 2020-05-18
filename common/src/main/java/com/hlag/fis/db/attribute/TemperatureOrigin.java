package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TemperatureOrigin implements PersistableEnum<String> {
    NONE(" "),
	D("D");

	private String value;

	TemperatureOrigin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<TemperatureOrigin, String> {
		public Converter() {
			super(TemperatureOrigin.class);
		}
	}
}