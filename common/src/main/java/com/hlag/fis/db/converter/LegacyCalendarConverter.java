package com.hlag.fis.db.converter;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LegacyCalendarConverter implements AttributeConverter<Timestamp, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(Timestamp d) {
		return d;
	}

	@Override
	public Timestamp convertToEntityAttribute(Timestamp d) {
        if (d == null) {
            return null;
        }
        if (d.after(Timestamp.valueOf(LocalDateTime.of(3000, 12, 31, 0, 0, 0)))) {
            return null;
        }
        if (d.before(Timestamp.valueOf(LocalDateTime.of(1900, 01, 01, 0, 0, 0)))) {
            return null;
        }
        return d;
    }
}
