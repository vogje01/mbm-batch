package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum HaulageType implements PersistableEnum<String> {
	NONE(""),
	CH("CH"),
	MH("MH"),
	MX("MX");

	private String value;

	HaulageType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<HaulageType, String> {
		public Converter() {
			super(HaulageType.class);
		}
	}
}
