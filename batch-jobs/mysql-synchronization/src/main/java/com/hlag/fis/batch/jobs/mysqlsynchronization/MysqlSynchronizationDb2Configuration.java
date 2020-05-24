package com.hlag.fis.batch.jobs.mysqlsynchronization;

import org.hibernate.SessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "db2EntityManagerFactory",
        transactionManagerRef = "db2TransactionManager",
        basePackages = {"com.hlag.fis.db.db2.repository"}
)
public class MysqlSynchronizationDb2Configuration {

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "db2deve.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean(name = "db2EntityManager")
    public EntityManager entityManagerBean() {
        return Objects.requireNonNull(entityManagerFactoryBean().getObject()).createEntityManager();
    }

    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("Db2PU");
        em.setJpaProperties(hibernateProperties());
        em.setPackagesToScan("com.hlag.fis.db.db2");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return Objects.requireNonNull(entityManagerFactoryBean().getObject()).unwrap(SessionFactory.class);
    }

    @Bean(name = "db2TransactionManager")
    public HibernateTransactionManager jpaTransactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "com.hlag.fis.db.db2.config.ExtendedDB2Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        hibernateProperties.setProperty("hibernate.c3p0.timeout", "900");
        hibernateProperties.setProperty("hibernate.c3p0.min_size", "5");
        hibernateProperties.setProperty("hibernate.c3p0.max_size", "20");
        hibernateProperties.setProperty("hibernate.c3p0.acquire_increment", "5");
        return hibernateProperties;
    }
}
