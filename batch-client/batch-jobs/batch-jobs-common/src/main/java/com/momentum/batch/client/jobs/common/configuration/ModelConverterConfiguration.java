package com.momentum.batch.client.jobs.common.configuration;

import com.momentum.batch.client.jobs.common.converter.ModelConverter;
import org.modelmapper.ModelMapper;
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
    public ModelConverter modelConverter(ModelMapper modelMapper) {
        return new ModelConverter(modelMapper);
    }
}
