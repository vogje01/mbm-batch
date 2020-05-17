package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum VolumeUnit implements PersistableEnum<String> {
    NONE(" "),
	BLL("BLL"),
	CBM("CBM"),
	CFT("CFT"),
	FTQ("FTQ"),
	GLI("GLI"),
	LTR("LTR"),
	MTQ("MTQ"),
	M3("M3");

	private String value;

	VolumeUnit(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<VolumeUnit, String> {
		public Converter() {
			super(VolumeUnit.class);
		}
	}
}