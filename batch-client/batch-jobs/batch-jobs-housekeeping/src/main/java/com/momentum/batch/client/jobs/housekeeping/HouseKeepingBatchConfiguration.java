package com.momentum.batch.client.jobs.housekeeping;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Batch management housekeeping batch job.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@Configuration
@EnableCaching
@EnableBatchProcessing
@EnableEncryptableProperties
@EnableJpaRepositories(basePackages = {"com.momentum.batch.server.database.repository"})
@EntityScan(basePackages = {"com.momentum.batch.server.database.domain"})
public class HouseKeepingBatchConfiguration extends DefaultBatchConfigurer {

    /**
     * Class path resources
     */
    private static final ClassPathResource[] configFiles = new ClassPathResource[]{new ClassPathResource("application.yml"), new ClassPathResource("houseKeeping.yml")};

    /**
     * Set job name.
     *
     * @return job name.
     */
    @Bean
    String jobName() {
        return "Housekeeping Batch";
    }

    /**
     * Sets the property sources.
     *
     * @return property source configurer.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(configFiles);
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return configurer;
    }

    /**
     * Overriding in order not to set datasource even if a datasource exist. Initialize will use a
     * Map based JobRepository (instead of a database).
     *
     * @param dataSource batch data source.
     */
    @Override
    public void setDataSource(DataSource dataSource) {

    }
}
