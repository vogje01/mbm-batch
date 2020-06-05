package com.hlag.fis.batch.configuration;

import com.hlag.fis.batch.logging.BatchLogProducer;
import com.hlag.fis.batch.logging.BatchLogger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class BatchLoggingConfiguration {

    @Bean
    @Scope("prototype")
    public BatchLogger batchLogger(final InjectionPoint ip, String hostName, String nodeName, BatchLogProducer batchLogProducer) {
        return new BatchLogger(hostName, nodeName, batchLogProducer, Objects.requireNonNull(ip.getMethodParameter()).getDeclaringClass());
    }
}
