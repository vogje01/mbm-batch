package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DistrSystem implements PersistableEnum<String> {
    NONE(" "),
    P("P"),
    S("S");

    String value;

    DistrSystem(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<DistrSystem, String> {
        public Converter() {
            super(DistrSystem.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<DistrSystem, String> {
        public MysqlConverter() {
            super(DistrSystem.class);
        }
    }
}
