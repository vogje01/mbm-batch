package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum CopyForTpf implements PersistableEnum<String> {
    NONE(" "),
	CC("CC"),
	CN("CN"),
	FF("FF"),
	IF("IF"),
	IS("IS"),
	NX("NX"),
	N1("N1"),
	N2("N2"),
	PC("PC"),
	SH("SH");

	private String value;

	CopyForTpf(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<CopyForTpf, String> {
		public Converter() {
			super(CopyForTpf.class);
		}
	}
}
