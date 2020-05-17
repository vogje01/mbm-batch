package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ProcProcessType implements PersistableEnum<String> {
    NONE(" "),
    I("I"),
    M("M"),
    N("N"),
    Q("Q"),
    S("S"),
    X("X");

    String value;

    ProcProcessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<ProcProcessType, String> {
        public Converter() {
            super(ProcProcessType.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<ProcProcessType, String> {
        public MysqlConverter() {
            super(ProcProcessType.class);
        }
    }
}
