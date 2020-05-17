package com.hlag.fis.db.attribute.documenationtinstruction;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum MdOpCuType implements PersistableEnum<String> {
    NONE(" "),
	BO("BO"),
	OP("OP");

	private String value;

	MdOpCuType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<MdOpCuType, String> {
		public Converter() {
			super(MdOpCuType.class);
		}
	}
}
