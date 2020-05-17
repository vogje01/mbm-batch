package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum NightIndicator implements PersistableEnum<String> {
    NONE(" "),
    D("D"),
    N("N"),
    Q("Q"),
    Y("Y");

    String value;

    NightIndicator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<NightIndicator, String> {
        public Converter() {
            super(NightIndicator.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<NightIndicator, String> {
        public MysqlConverter() {
            super(NightIndicator.class);
        }
    }
}
