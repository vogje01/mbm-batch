package com.hlag.fis.db.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DateSerializer extends StdSerializer<LocalDate> {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	public DateSerializer() {
		this(null);
	}

	public DateSerializer(Class t) {
		super(t);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
		gen.writeString(formatter.format(value));
	}
}
