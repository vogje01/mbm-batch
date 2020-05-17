package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum GrossWeightUnit implements PersistableEnum<String> {
    NONE(" "),
	PCT("PCT"),
	GRM("GRM"),
	KG("KG"),
	KGM("KGM"),
	KGS("KGS"),
	LB("LB"),
	LBR("LBR"),
	LBS("LBS"),
	LTN("LTN"),
	STN("STN"),
	TNE("TNE");

	private String value;

	GrossWeightUnit(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<GrossWeightUnit, String> {
		public Converter() {
			super(GrossWeightUnit.class);
		}
	}
}