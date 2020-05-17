package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationLifecycleDirection implements PersistableEnum<String> {
    NONE(" "),
	I("I"),
	O("O"),
	N("N"),
	X("X");

	private String value;

    DocumentationLifecycleDirection(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationLifecycleDirection, String> {
		public Converter() {
			super(DocumentationLifecycleDirection.class);
		}
	}
}
