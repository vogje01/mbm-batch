package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum SignedCarrierType implements PersistableEnum<String> {
    NONE(" "),
	M("M"),
	O("O"),
	P("P");

	private String value;

	SignedCarrierType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<SignedCarrierType, String> {
		public Converter() {
			super(SignedCarrierType.class);
		}
	}
}
