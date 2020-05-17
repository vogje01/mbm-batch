package com.hlag.fis.db.attribute.transportunitpoint;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TransportUnitPointContEmptyFull implements PersistableEnum<String> {
    NONE(" "),
    E("E"),
    F("F");

    String value;

    TransportUnitPointContEmptyFull(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<TransportUnitPointContEmptyFull, String> {
        public Converter() {
            super(TransportUnitPointContEmptyFull.class);
        }
    }
}
