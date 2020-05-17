package com.hlag.fis.db.attribute.transportunitpoint;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TransportUnitPointClContainerized implements PersistableEnum<String> {
    NONE(" "),
    C("C"),
    N("N");

    String value;

    TransportUnitPointClContainerized(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNullValue() {
        return NONE.value;
    }

    public static class Converter extends LegacyEnumConverter<TransportUnitPointClContainerized, String> {
        public Converter() {
            super(TransportUnitPointClContainerized.class);
        }
    }
}
