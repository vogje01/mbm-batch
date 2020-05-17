package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ClDocReqType implements PersistableEnum<String> {
    NONE(" "),
	D("D"),
	M("M");

	String value;

	ClDocReqType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ClDocReqType, String> {
		public Converter() {
			super(ClDocReqType.class);
		}
	}
}
