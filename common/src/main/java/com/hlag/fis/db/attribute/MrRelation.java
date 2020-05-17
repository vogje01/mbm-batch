package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MrRelation implements PersistableEnum<String> {
	NONE(""),
	AA("AA"),
	AE("AE"),
	AF("AF"),
	AL("AL"),
	AM("AM"),
	AN("AN"),
	AO("AO"),
	AW("AW"),
	EA("EA"),
	EE("EE"),
	EF("EF"),
	EL("EL"),
	EM("EM"),
	EN("EN"),
	EO("EO"),
	ES("ES"),
	EW("EW"),
	EX("EX"),
	FA("FA"),
	FE("FE"),
	FF("FF"),
	FL("FL"),
	FN("FN"),
	LA("LA"),
	LE("LE"),
	LF("LF"),
	LL("LL"),
	LM("LM"),
	LN("LN"),
	LO("LO"),
	MA("MA"),
	ME("ME"),
	ML("ML"),
	MM("MM"),
	MN("MN"),
	MO("MO"),
	NA("NA"),
	NE("NE"),
	NF("NF"),
	NL("NL"),
	NN("NN"),
	NM("NM"),
	NO("NO"),
	OA("OA"),
	OE("OE"),
	OL("OL"),
	OM("OM"),
	ON("ON"),
	WA("WA"),
	WE("WE"),
	WW("WW"),
	XE("XE");

	private String value;

	MrRelation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MrRelation, String> {
		public Converter() {
			super(MrRelation.class);
		}
	}
}
