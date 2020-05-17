package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum OutputSystem implements PersistableEnum<String> {
    NONE(" "),
    P("P");

    String value;

    OutputSystem(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<OutputSystem, String> {
        public Converter() {
            super(OutputSystem.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<OutputSystem, String> {
        public MysqlConverter() {
            super(OutputSystem.class);
        }
    }
}
