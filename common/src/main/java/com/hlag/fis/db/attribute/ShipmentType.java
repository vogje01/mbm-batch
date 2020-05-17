package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ShipmentType implements PersistableEnum<String> {
	NONE(""),
	BO("BO"),
	NB("NB");

	private String value;

	ShipmentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ShipmentType, String> {
		public Converter() {
			super(ShipmentType.class);
		}
	}
}
