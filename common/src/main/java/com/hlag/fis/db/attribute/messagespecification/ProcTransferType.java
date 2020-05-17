package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum ProcTransferType implements PersistableEnum<String> {
    NONE(" "),
    S("S");

    String value;

    ProcTransferType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<ProcTransferType, String> {
        public Converter() {
            super(ProcTransferType.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<ProcTransferType, String> {
        public MysqlConverter() {
            super(ProcTransferType.class);
        }
    }
}
