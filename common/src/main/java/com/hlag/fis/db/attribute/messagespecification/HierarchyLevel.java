package com.hlag.fis.db.attribute.messagespecification;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum HierarchyLevel implements PersistableEnum<String> {
    NONE(" "),
    NT("NT"),
    SK("SK");

    String value;

    HierarchyLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<HierarchyLevel, String> {
        public Converter() {
            super(HierarchyLevel.class);
        }
    }

    public static class MysqlConverter extends LegacyMysqlEnumConverter<HierarchyLevel, String> {
        public MysqlConverter() {
            super(HierarchyLevel.class);
        }
    }
}
