package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationLifecycleImportExportFlag implements PersistableEnum<String> {
    NONE(" "),
	E("E"),
	I("I"),
	B("B");

	private String value;

    DocumentationLifecycleImportExportFlag(String id) {
		this.value = id;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationLifecycleImportExportFlag, String> {
		public Converter() {
			super(DocumentationLifecycleImportExportFlag.class);
		}
	}
}

