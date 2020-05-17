package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationLifecycleMedium implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	F("F"),
	E("E"),
	M("M"),
	W("W"),
	O("O"),
	T("T"),
	I("I"),
	P("P");

	private String value;

	private DocumentationLifecycleMedium(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationLifecycleMedium, String> {
		public Converter() {
			super(DocumentationLifecycleMedium.class);
		}
	}
}