package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum PrintContact implements PersistableEnum<String> {
    NONE(" "),
	E("E"),
	I("I"),
	N("N"),
	Y("Y");

	String value;

	PrintContact(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<PrintContact, String> {
		public Converter() {
			super(PrintContact.class);
		}
	}
}
