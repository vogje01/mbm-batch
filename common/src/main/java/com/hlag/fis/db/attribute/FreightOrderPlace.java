package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum FreightOrderPlace implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	B("B"),
	C("C");

	String value;

	FreightOrderPlace(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<FreightOrderPlace, String> {

		public Converter() {
			super(FreightOrderPlace.class);
		}
	}
}
