package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MdType implements PersistableEnum<String> {
	NONE(""),
	BL("BL"),
	EE("EE"),
	EF("EF"),
	ES("ES"),
	EV("EV"),
	EX("EX"),
	OE("OE"),
	OV("OV"),
	SW("SW");

	private String value;

	MdType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MdType, String> {
		public Converter() {
			super(MdType.class);
		}
	}
}
