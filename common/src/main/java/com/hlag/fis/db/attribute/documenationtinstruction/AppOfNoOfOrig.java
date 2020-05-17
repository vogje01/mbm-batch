package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum AppOfNoOfOrig implements PersistableEnum<String> {
    NONE(" "),
	N("N"),
	T("T");

	String value;

	AppOfNoOfOrig(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<AppOfNoOfOrig, String> {
		public Converter() {
			super(AppOfNoOfOrig.class);
		}
	}
}
