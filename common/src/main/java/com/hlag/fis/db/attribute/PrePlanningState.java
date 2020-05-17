package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum PrePlanningState implements PersistableEnum<String> {
    NONE(" "),
	CF("CF"),
	LK("LK"),
	RJ("RJ"),
	SE("SE");

	String value;

	PrePlanningState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<PrePlanningState, String> {
		public Converter() {
			super(PrePlanningState.class);
		}
	}
}
