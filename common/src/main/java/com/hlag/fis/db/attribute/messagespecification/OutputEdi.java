package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum OutputEdi implements PersistableEnum<String> {
    NONE(" "),
    E("E"),
    N("N"),
    P("P");

    String value;

    OutputEdi(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<OutputEdi, String> {
        public Converter() {
            super(OutputEdi.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<OutputEdi, String> {
        public MysqlConverter() {
            super(OutputEdi.class);
        }
    }
}
