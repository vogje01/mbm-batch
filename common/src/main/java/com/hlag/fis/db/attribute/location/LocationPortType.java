package com.hlag.fis.db.attribute.location;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum LocationPortType implements PersistableEnum<String> {
    NONE(" "),
	N("N"),
	S("S");

	String value;

	LocationPortType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<LocationPortType, String> {
		public Converter() {
			super(LocationPortType.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<LocationPortType, String> {
        public MysqlConverter() {
            super(LocationPortType.class);
        }
    }

}
