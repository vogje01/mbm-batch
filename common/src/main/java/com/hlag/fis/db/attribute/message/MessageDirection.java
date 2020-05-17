package com.hlag.fis.db.attribute.message;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MessageDirection implements PersistableEnum<String> {
    NONE(" "),
	I("I"),
	O("O");

	String value;

	MessageDirection(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MessageDirection, String> {
		public Converter() {
			super(MessageDirection.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<MessageDirection, String> {
        public MysqlConverter() {
            super(MessageDirection.class);
        }
    }
}
