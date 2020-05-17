package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum AttachListPrint implements PersistableEnum<String> {
    NONE(" "),
	N("N"),
	P("P"),
	T("T"),;

	String value;

	AttachListPrint(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<AttachListPrint, String> {
		public Converter() {
			super(AttachListPrint.class);
		}
	}
}
