package com.hlag.fis.db.converter;

import com.google.common.base.CharMatcher;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Qualifier("db2EntityManagerFactory")
public class LegacyStringConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String s) {
		return s != null && CharMatcher.ascii().matchesAllOf(s) ? s : " ";
	}

	@Override
	public String convertToEntityAttribute(String s) {
		return s == null || s.trim().isEmpty() || !CharMatcher.ascii().matchesAllOf(s) ? " " : s.trim();
	}
}

