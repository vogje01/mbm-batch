package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ShipmentStatus implements PersistableEnum<String> {
    NONE(" "),
	AC("AC"),
	IS("IS"),
	NI("NI"),
	RE("RE");

	private String value;

	ShipmentStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ShipmentStatus, String> {
		public Converter() {
			super(ShipmentStatus.class);
		}
	}
}
