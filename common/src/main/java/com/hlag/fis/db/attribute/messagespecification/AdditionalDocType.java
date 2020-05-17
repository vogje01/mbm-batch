package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum AdditionalDocType implements PersistableEnum<String> {
    NONE(" "),
    B("B"),
    C("C"),
    L("L"),
    X("X");

    String value;

    AdditionalDocType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<AdditionalDocType, String> {
        public Converter() {
            super(AdditionalDocType.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<AdditionalDocType, String> {
        public MysqlConverter() {
            super(AdditionalDocType.class);
        }
    }
}
