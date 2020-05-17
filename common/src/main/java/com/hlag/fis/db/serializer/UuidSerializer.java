package com.hlag.fis.db.serializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

public class UuidSerializer<T extends PrimaryKeyIdentifier> extends StdConverter<T, String> {

	@Override
	public String convert(T t) {
		return t.getId().toString();
	}
}
