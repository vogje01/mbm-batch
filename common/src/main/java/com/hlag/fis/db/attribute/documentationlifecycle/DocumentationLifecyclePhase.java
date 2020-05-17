package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationLifecyclePhase implements PersistableEnum<String> {
    NONE(" "),
	O("O"),
	C("C"),
	D("D"),
	R("R");

	private String value;

	DocumentationLifecyclePhase(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationLifecyclePhase, String> {
		public Converter() {
			super(DocumentationLifecyclePhase.class);
		}
	}
}