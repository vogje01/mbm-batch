package com.hlag.fis.batch.jobs.housekeepingbatch;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableCaching
@EnableBatchProcessing
@EnableJpaRepositories(basePackages = {"com.hlag.fis.batch.repository"})
public class HouseKeepingBatchConfiguration {

    private static final ClassPathResource[] configFiles = new ClassPathResource[]{new ClassPathResource("application.yml"), new ClassPathResource("houseKeeping.yml")};

    @Bean
    String jobName() {
        return "Housekeeping Batch";
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(configFiles);
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return configurer;
    }

    @Bean
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("MysqlPU");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        em.setPackagesToScan("com.hlag.fis.db.mysql", "com.hlag.fis.batch");
        return em;
    }

    final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        return hibernateProperties;
    }

    public SessionFactory sessionFactory() {
        return Objects.requireNonNull(entityManagerFactory().getObject()).unwrap(SessionFactory.class);
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

}
