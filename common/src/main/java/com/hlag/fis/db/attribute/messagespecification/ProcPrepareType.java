package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ProcPrepareType implements PersistableEnum<String> {
    NONE(" "),
    I("I");

    String value;

    ProcPrepareType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<ProcPrepareType, String> {
        public Converter() {
            super(ProcPrepareType.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<ProcPrepareType, String> {
        public MysqlConverter() {
            super(ProcPrepareType.class);
        }
    }
}
