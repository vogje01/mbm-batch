package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TransportUnitPointState implements PersistableEnum<String> {
	NONE(""),
	A("A"),
	B("B"),
	C("C"),
	D("D"),
	I("I"),
	N("N"),
	P("P"),
	R("R"),
	W("W");

	private String value;

	TransportUnitPointState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<TransportUnitPointState, String> {
		public Converter() {
			super(TransportUnitPointState.class);
		}
	}
}
