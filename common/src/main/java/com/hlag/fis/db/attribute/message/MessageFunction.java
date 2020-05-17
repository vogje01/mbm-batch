package com.hlag.fis.db.attribute.message;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MessageFunction implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	O("O"),
	R("R");

	String value;

	MessageFunction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MessageFunction, String> {
		public Converter() {
			super(MessageFunction.class);
		}
	}

	public static class MysqlConverter extends LegacyMysqlEnumConverter<MessageFunction, String> {
		public MysqlConverter() {
			super(MessageFunction.class);
		}
	}
}
