package com.hlag.fis.db.converter;

import javax.persistence.AttributeConverter;
import java.sql.Time;
import java.time.LocalTime;

public class LegacyTimeConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime t) {
        return t != null ? Time.valueOf(t) : Time.valueOf(LocalTime.MIDNIGHT);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time t) {
        return t != null ? t.toLocalTime() : LocalTime.MIDNIGHT;
    }
}
