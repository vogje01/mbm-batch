package com.hlag.fis.db.attribute.message;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MessageJobClass implements PersistableEnum<String> {
    NONE(" "),
	A("A"),
	B("B"),
	C("C"),
	D("D"),
	E("E"),
	F("F"),
	H("H"),
	L("L"),
	M("M"),
	N("N"),
	P("P");

	String value;

	MessageJobClass(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MessageJobClass, String> {
		public Converter() {
			super(MessageJobClass.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<MessageJobClass, String> {
        public MysqlConverter() {
            super(MessageJobClass.class);
        }
    }
}
