package com.hlag.fis.db.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;

/**
 * Legacy ENUM converter.
 *
 * @param <T> value type.
 * @param <E> enum type.
 */
public abstract class LegacyEnumConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {

    private static final Logger logger = LoggerFactory.getLogger(LegacyEnumConverter.class);

    private final Class<T> clazz;

    public LegacyEnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    private boolean isValid(String lcValidStateA) {
        return lcValidStateA.matches("[a-zA-Z]+");
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getValue() : attribute.getNullValue();
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        if(dbData == null || dbData.toString().trim().isEmpty()) {
            return T.valueOf(clazz, "NONE");
        }
        if (!isValid(dbData.toString().trim())) {
            return T.valueOf(clazz, "NONE");
        }
        T[] enums = clazz.getEnumConstants();
        for (T e : enums) {
            if (dbData.toString().trim().equals(e.getValue())) {
                return e;
            }
        }
        logger.error("Invalid value - class: " + clazz.getSimpleName() + " value: '" + dbData + "'");
        return T.valueOf(clazz, "NONE");
    }
}