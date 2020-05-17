package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ShipmentMainType implements PersistableEnum<String> {
    NONE(" "),
	CO("CO"),
	CU("CU"),
	MT("MT"),
	PT("PT"),
	SC("SC");

	private String value;

	ShipmentMainType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ShipmentMainType, String> {
		public Converter() {
			super(ShipmentMainType.class);
		}
	}
}
