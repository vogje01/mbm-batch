package com.hlag.fis.db.attribute.location;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum LocationClSource implements PersistableEnum<String> {
    NONE(" "),
	E("E"),
	I("I");

	String value;

	LocationClSource(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<LocationClSource, String> {
		public Converter() {
			super(LocationClSource.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<LocationClSource, String> {
        public MysqlConverter() {
            super(LocationClSource.class);
        }
    }
}
