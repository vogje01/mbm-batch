package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ModeOfTransport implements PersistableEnum<String> {
    NONE(" "),
	CR("CR"),
	CW("CW"),
	IL("IL"),
	OV("OV"),
	RA("RA"),
	TR("TR"),
	VE("VE"),
	WW("WW");

	String value;

	ModeOfTransport(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<ModeOfTransport, String> {
		public Converter() {
			super(ModeOfTransport.class);
		}
	}
}