package com.hlag.fis.db.converter;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Converts a java.sql.Date to a java.time.LocalDate taken into account that the SQL date is in UTC and the local date
 * in the default time zone.
 */
public class LegacyDateConverter implements AttributeConverter<LocalDate, Date> {

    private static final Date MIN_DATE = Date.valueOf(LocalDate.of(1, 1, 1));
    private static final Date MIN_DATE2 = Date.valueOf(LocalDate.of(1900, 1, 1));
    private static final Date MAX_DATE = Date.valueOf(LocalDate.of(9999, 12, 31));

    @Override
    public Date convertToDatabaseColumn(LocalDate d) {
        return Date.valueOf(d);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date d) {
        return convertLegacyDate(d);
    }

    static public LocalDate convertLegacyDate(Date d) {
        return d == null || d.equals(MIN_DATE) || d.equals(MIN_DATE2) || d.equals(MAX_DATE) ? null : d.toLocalDate();
    }

    static public LocalDateTime convertLegacyDateTime(Date d, Time t) {
        LocalDate convertedDate = d == null || d.equals(MIN_DATE) || d.equals(MIN_DATE2) || d.equals(MAX_DATE) ? null : d.toLocalDate();
        LocalTime convertedTime = t == null ? null : t.toLocalTime();
        return convertedDate != null ? LocalDateTime.of(convertedDate, convertedTime) : null;
    }
}
