package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ServiceType implements PersistableEnum<String> {
    NONE(" "),
	CIC("CIC"),
	CIT("CIT"),
	CPC("CPC"),
	CPT("CPT"),
	HOU("HOU");

	private String value;

	ServiceType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ServiceType, String> {
		public Converter() {
			super(ServiceType.class);
		}
	}
}
