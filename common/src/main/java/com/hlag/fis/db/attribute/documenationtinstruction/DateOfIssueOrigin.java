package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DateOfIssueOrigin implements PersistableEnum<String> {
    NONE(" "),
	D("D"),
	F("F"),
	P("P");

	private String value;

	DateOfIssueOrigin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DateOfIssueOrigin, String> {
		public Converter() {
			super(DateOfIssueOrigin.class);
		}
	}
}
