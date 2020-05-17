package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum UsFlagRestrBy implements PersistableEnum<String> {
    NONE(" "),
	A1("A1"),
	A2("A2"),
	A3("A3"),
	CO("CO"),
	EX("EX"),
	ST("ST"),
	TC("TC"),
	TG("TG");

	private String value;

	UsFlagRestrBy(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<UsFlagRestrBy, String> {
		public Converter() {
			super(UsFlagRestrBy.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<UsFlagRestrBy, String> {
        public MysqlConverter() {
            super(UsFlagRestrBy.class);
        }
    }
}
