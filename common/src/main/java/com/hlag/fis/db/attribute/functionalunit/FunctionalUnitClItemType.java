package com.hlag.fis.db.attribute.functionalunit;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum FunctionalUnitClItemType implements PersistableEnum<String> {
    NONE(" "),
	B("B"),
	G("G"),
	M("M"),
	P("P"),
	S("S"),
	V("V"),
	X("X");

	String value;

	FunctionalUnitClItemType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<FunctionalUnitClItemType, String> {
		public Converter() {
			super(FunctionalUnitClItemType.class);
		}
	}
}
