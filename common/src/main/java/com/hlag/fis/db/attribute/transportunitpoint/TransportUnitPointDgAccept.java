package com.hlag.fis.db.attribute.transportunitpoint;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TransportUnitPointDgAccept implements PersistableEnum<String> {
    NONE(" "),
    NO("NO"),
    OK("OK"),
    PE("PE"),
    Y("Y");

    String value;

    TransportUnitPointDgAccept(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<TransportUnitPointDgAccept, String> {
        public Converter() {
            super(TransportUnitPointDgAccept.class);
        }
    }
}
