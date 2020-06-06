package com.momentum.batch.client.jobs.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momentum.batch.client.jobs.common.converter.ModelConverter;
import com.momentum.batch.client.jobs.common.converter.ModelConverterHelper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class ModelConverterConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public ModelConverter modelConverter(ObjectMapper objectMapper) {
        return new ModelConverter(modelMapper(), modelConverterHelper(objectMapper));
    }

    @Bean
    public ModelConverterHelper modelConverterHelper(ObjectMapper objectMapper) {
        return new ModelConverterHelper(objectMapper);
    }
}
