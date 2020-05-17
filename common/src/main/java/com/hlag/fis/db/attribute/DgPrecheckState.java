package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DgPrecheckState implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	I("I"),
	P("P");

	String value;

	DgPrecheckState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DgPrecheckState, String> {
		public Converter() {
			super(DgPrecheckState.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<DgPrecheckState, String> {
        public MysqlConverter() {
            super(DgPrecheckState.class);
        }
    }
}
