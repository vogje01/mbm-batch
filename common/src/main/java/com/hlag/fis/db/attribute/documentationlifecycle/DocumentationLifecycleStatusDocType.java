package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationLifecycleStatusDocType implements PersistableEnum<String> {
    NONE(" "),
	SI("SI"),
	AN("AN"),
	MD("MD"),
	HB("HB"),
	DO("DO"),
	SN("SN");

	private String value;

	DocumentationLifecycleStatusDocType(String id) {
		this.value = id;
	}

	@Override
	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationLifecycleStatusDocType, String> {
		public Converter() {
			super(DocumentationLifecycleStatusDocType.class);
		}
	}
}
