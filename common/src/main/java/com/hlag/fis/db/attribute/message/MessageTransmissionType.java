package com.hlag.fis.db.attribute.message;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MessageTransmissionType implements PersistableEnum<String> {
    NONE(" "),
	E("E"),
	I("I");

	String value;

	MessageTransmissionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MessageTransmissionType, String> {
		public Converter() {
			super(MessageTransmissionType.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<MessageTransmissionType, String> {
        public MysqlConverter() {
            super(MessageTransmissionType.class);
        }
    }
}
