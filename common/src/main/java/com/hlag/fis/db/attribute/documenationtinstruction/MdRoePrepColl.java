package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MdRoePrepColl implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	C("C"),
	P("P");

	private String value;

	MdRoePrepColl(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MdRoePrepColl, String> {
		public Converter() {
			super(MdRoePrepColl.class);
		}
	}
}
