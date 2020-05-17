package com.hlag.fis.db.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Legacy MySQL ENUM converter.
 * <p>
 * All empty values, as well as all value, which are equivalences of T.NONE will be converted to NULL.
 * </p>
 *
 * @param <T> value type.
 * @param <E> enum type.
 */
@Converter
public class LegacyMysqlEnumConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {

    private static final Logger logger = LoggerFactory.getLogger(LegacyMysqlEnumConverter.class);

    private final Class<T> clazz;

    public LegacyMysqlEnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        logger.info("Attribute: " + attribute.toString());
        logger.info("Conversion: " + (attribute.getValue() != T.valueOf(clazz, "NONE") ? attribute.getValue() : null));
        return attribute.ordinal() == 0 ? null : attribute.getValue();
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        if (dbData == null || dbData.toString().trim().isEmpty()) {
            return null;
        }
        T[] enums = clazz.getEnumConstants();
        for (T e : enums) {
            if (dbData.toString().trim().equals(e.getValue())) {
                return e;
            }
        }
        logger.error("Invalid value - class: " + clazz.getSimpleName() + " value: '" + dbData + "'");
        return null;
    }
}