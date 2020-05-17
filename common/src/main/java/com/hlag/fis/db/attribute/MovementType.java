package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MovementType implements PersistableEnum<String> {
    NONE(" "),
	BB("BB"),
	FCL("FCL"),
	LCL("LCL");

	private String value;

	MovementType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MovementType, String> {
		public Converter() {
			super(MovementType.class);
		}
	}
}
