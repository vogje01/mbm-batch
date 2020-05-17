package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum JobClass implements PersistableEnum<String> {
    NONE(" "),
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    H("H"),
    L("L"),
    M("M"),
    N("N"),
    P("P");

    String value;

    JobClass(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<JobClass, String> {
        public Converter() {
            super(JobClass.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<JobClass, String> {
        public MysqlConverter() {
            super(JobClass.class);
        }
    }
}
