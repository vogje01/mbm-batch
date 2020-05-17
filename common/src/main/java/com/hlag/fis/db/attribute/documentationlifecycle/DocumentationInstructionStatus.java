package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationInstructionStatus implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	I("I");

	String value;

	DocumentationInstructionStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationInstructionStatus, String> {
		public Converter() {
			super(DocumentationInstructionStatus.class);
		}
	}
}
