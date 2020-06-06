package com.momentum.batch.client.jobs.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ExecutionContext;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class ModelConverterHelper {

    private ObjectMapper objectMapper;

    public ModelConverterHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String jsonSerialize(ExecutionContext executionContext) {
        try {
            return objectMapper.writer().writeValueAsString(executionContext);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
