package com.hlag.fis.db.attribute.message;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MessageState implements PersistableEnum<String> {
    NONE(" "),
	D("D"),
	L("L"),
	P("P"),
	R("R");

	String value;

	MessageState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MessageState, String> {
		public Converter() {
			super(MessageState.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<MessageState, String> {
        public MysqlConverter() {
            super(MessageState.class);
        }
    }
}
