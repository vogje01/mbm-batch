package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum GrossWeightOrigin implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	C("C"),
	D("D"),
	E("E"),
	V("V");

	String value;

	GrossWeightOrigin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<GrossWeightOrigin, String> {
		public Converter() {
			super(GrossWeightOrigin.class);
		}
	}
}
