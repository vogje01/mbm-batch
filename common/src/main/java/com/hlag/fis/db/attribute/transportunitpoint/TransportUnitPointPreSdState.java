package com.hlag.fis.db.attribute.transportunitpoint;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TransportUnitPointPreSdState implements PersistableEnum<String> {
    NONE(" "),
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    I("I"),
    N("N"),
    P("P");

    String value;

    TransportUnitPointPreSdState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<TransportUnitPointPreSdState, String> {
        public Converter() {
            super(TransportUnitPointPreSdState.class);
        }
    }
}
