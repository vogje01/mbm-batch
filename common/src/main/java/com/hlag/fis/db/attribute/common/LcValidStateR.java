package com.hlag.fis.db.attribute.common;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum LcValidStateR implements PersistableEnum<String> {
    NONE(" "),
	D("D"),
	R("R");

	String value;

	LcValidStateR(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<LcValidStateR, String> {
		public Converter() {
			super(LcValidStateR.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<LcValidStateR, String> {
        public MysqlConverter() {
            super(LcValidStateR.class);
        }
    }
}
