package com.hlag.fis.db.converter;

import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Qualifier("db2EntityManagerFactory")
public class LegacyBooleanIntegerConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean aBoolean) {
		return aBoolean ? "1" : "0";
	}

	@Override
	public Boolean convertToEntityAttribute(String s) {
		return s != null && !s.trim().isEmpty() && !" ".equals(s) && "1".equalsIgnoreCase(s);
	}
}

