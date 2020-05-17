package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentStatus implements PersistableEnum<String> {
    NONE(" "),
	D("D"),
	P("P");

	String value;

	DocumentStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentStatus, String> {
		public Converter() {
			super(DocumentStatus.class);
		}
	}
}
