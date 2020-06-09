package com.momentum.batch.client.jobs.common.converter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.momentum.batch.util.ContextSerializer;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
public class ModelConverterHelper {

    private ObjectMapper objectMapper;

    public ModelConverterHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String jsonSerialize(ExecutionContext executionContext) {
        try {
            ExecutionContextDto executionContextDto = new ExecutionContextDto(executionContext);
            return objectMapper.writer().writeValueAsString(executionContextDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class ExecutionContextDto {

        @JsonProperty(value = "dirty")
        private boolean dirty;

        @JsonProperty(value = "empty")
        private boolean empty;

        @JsonSerialize(using = ContextSerializer.class)
        private Map<String, Object> executionContext = new HashMap<>();

        private ExecutionContextDto(ExecutionContext context) {
            context.entrySet().forEach(e -> executionContext.put(e.getKey(), e.getValue()));
        }
    }
}
