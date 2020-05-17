package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum CyCfsPrint implements PersistableEnum<String> {
    NONE(" "),
	B("B"),
	F("F"),
	N("N");

	String value;

	CyCfsPrint(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<CyCfsPrint, String> {
		public Converter() {
			super(CyCfsPrint.class);
		}
	}
}
