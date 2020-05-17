package com.hlag.fis.db.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateTimeSerializer extends StdSerializer<LocalDateTime> {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");

	public DateTimeSerializer() {
		this(null);
	}

	public DateTimeSerializer(Class t) {
		super(t);
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
		gen.writeString(formatter.format(value));
	}
}
