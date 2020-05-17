package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum AppOfCharge implements PersistableEnum<String> {
    NONE(" "),
	B("B"),
	BI("BI"),
	C("C"),
	CI("CI"),
	N("N"),
	P("P"),
	PI("PI");

	String value;

	AppOfCharge(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<AppOfCharge, String> {

		public Converter() {
			super(AppOfCharge.class);
		}
	}
}
