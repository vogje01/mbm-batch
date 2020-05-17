package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum RddChangeReason implements PersistableEnum<String> {
    NONE(" "),
	GAD("GAD"),
	GCR("GCR"),
	GST("GST"),
	HCD("HCD");

	String value;

	RddChangeReason(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<RddChangeReason, String> {
		public Converter() {
			super(RddChangeReason.class);
		}
	}

    public static class MysqlConverter extends LegacyMysqlEnumConverter<RddChangeReason, String> {
        public MysqlConverter() {
            super(RddChangeReason.class);
        }
    }

}
