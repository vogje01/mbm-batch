package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationLifecycleStatusPartyFuntion implements PersistableEnum<String> {
    NONE(" "),
	CU("CU"),
	FF("FF");

	private String value;

	private DocumentationLifecycleStatusPartyFuntion(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationLifecycleStatusPartyFuntion, String> {
		public Converter() {
			super(DocumentationLifecycleStatusPartyFuntion.class);
		}
	}
}
