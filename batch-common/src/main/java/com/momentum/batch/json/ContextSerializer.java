package com.momentum.batch.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class ContextSerializer extends JsonSerializer<Map<String, Object>> {

    @Override
    public void serialize(final Map<String, Object> value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {

        // Iterate the map entries and write them as fields
        jgen.writeStartObject();
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            jgen.writeObjectField(entry.getKey(), entry.getValue());
        }
        jgen.writeEndObject();
    }
}

