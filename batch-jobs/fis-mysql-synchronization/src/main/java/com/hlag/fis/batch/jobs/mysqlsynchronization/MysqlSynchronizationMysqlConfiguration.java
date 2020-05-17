package com.hlag.fis.batch.jobs.mysqlsynchronization;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.hibernate.SessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager",
        basePackages = {"com.hlag.fis.db.mysql.repository"}
)
public class MysqlSynchronizationMysqlConfiguration {

    private static final String[] cacheNames = {"ClientAuthorization", "Client", "ClientRole", "DocumentationRequest",
            "DocumentationInstruction", "DocumentationLifecycle", "GeoHierarchy", "FunctionalUnit", "Location", "Message",
            "MessageSpecification", "OrganizationPlace", "PlannedShipment", "SecurityOrganization", "TransportUnitPoint",
            "TrustworthExclusion", "Users", "UserRole", "UserAuthorization"};

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("MysqlPU");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        em.setPackagesToScan("com.hlag.fis.db.mysql", "com.hlag.fis.batch.model");
        return em;
    }

    final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        return hibernateProperties;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return Objects.requireNonNull(entityManagerFactoryBean().getObject()).unwrap(SessionFactory.class);
    }

    @Bean(name = "mysqlTransactionManager")
    public HibernateTransactionManager jpaTransactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .recordStats();
    }
}
