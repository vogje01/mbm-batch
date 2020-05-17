package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentType implements PersistableEnum<String> {
    NONE(" "),
	AN("AN"),
	DO("DO"),
	HB("HB"),
	MD("MD");

	String value;

	DocumentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentType, String> {
		public Converter() {
			super(DocumentType.class);
		}
	}
}
