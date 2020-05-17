package com.hlag.fis.db.attribute;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum TrpnStatus implements PersistableEnum<String> {
    NONE(" "),
	C("C"),
	I("I");

	private String value;

	TrpnStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<TrpnStatus, String> {
		public Converter() {
			super(TrpnStatus.class);
		}
	}
}
