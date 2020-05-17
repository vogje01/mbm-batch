package com.hlag.fis.db.converter;

import com.google.common.base.CharMatcher;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LegacyMysqlStringConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String s) {
        return s != null && !s.trim().isEmpty() && CharMatcher.ascii().matchesAllOf(s) ? s : null;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return s == null || s.trim().isEmpty() || !CharMatcher.ascii().matchesAllOf(s) ? null : s.trim();
    }
}

