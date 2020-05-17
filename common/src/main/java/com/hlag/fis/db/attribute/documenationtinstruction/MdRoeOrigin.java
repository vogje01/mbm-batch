package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MdRoeOrigin implements PersistableEnum<String> {
    NONE(" "),
	ASI("ASI"),
	ECB("ECB"),
	EU1("EU1"),
	GEN("GEN"),
	IRR("IRR");

	private String value;

	MdRoeOrigin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MdRoeOrigin, String> {
		public Converter() {
			super(MdRoeOrigin.class);
		}
	}
}
