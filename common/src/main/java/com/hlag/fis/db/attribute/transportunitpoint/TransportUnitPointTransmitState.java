package com.hlag.fis.db.attribute.transportunitpoint;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TransportUnitPointTransmitState implements PersistableEnum<String> {
    NONE(" "),
    S("S"),
    W("W"),
    X("X");

    String value;

    TransportUnitPointTransmitState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<TransportUnitPointTransmitState, String> {
        public Converter() {
            super(TransportUnitPointTransmitState.class);
        }
    }
}
