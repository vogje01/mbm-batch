package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum PropagationState implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	N("N");

	private String value;

	PropagationState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<PropagationState, String> {
		public Converter() {
			super(PropagationState.class);
		}
	}
}
