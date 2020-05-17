package com.hlag.fis.db.attribute.common;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum LcValidStateA implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	D("D"),
	H("H"),
	P("P");

	String value;

	LcValidStateA(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<LcValidStateA, String> {
		public Converter() {
			super(LcValidStateA.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<LcValidStateA, String> {
        public MysqlConverter() {
            super(LcValidStateA.class);
        }
    }
}
