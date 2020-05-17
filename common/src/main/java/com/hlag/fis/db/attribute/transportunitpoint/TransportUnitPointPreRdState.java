package com.hlag.fis.db.attribute.transportunitpoint;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TransportUnitPointPreRdState implements PersistableEnum<String> {
    NONE(" "),
    C("C"),
    I("I"),
    N("N"),
    P("P"),
    R("R"),
    W("W");

    String value;

    TransportUnitPointPreRdState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<TransportUnitPointPreRdState, String> {
        public Converter() {
            super(TransportUnitPointPreRdState.class);
        }
    }
}
