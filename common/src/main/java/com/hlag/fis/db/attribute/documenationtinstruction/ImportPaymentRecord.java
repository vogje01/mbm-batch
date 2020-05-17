package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ImportPaymentRecord implements PersistableEnum<String> {
    NONE(" "),
	Y("Y"),
	N("N"),
	V("V");

	String value;

	ImportPaymentRecord(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ImportPaymentRecord, String> {
		public Converter() {
			super(ImportPaymentRecord.class);
		}
	}
}
