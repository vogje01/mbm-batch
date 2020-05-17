package com.hlag.fis.db.mysql.config;

/*@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityManagerFactory", basePackages = {"com.hlag.fis.db.mysql"})
@EntityScan({"com.hlag.fis.db.mysql.model", "com.hlag.fis.batch.model"})
public class MysqlConfiguration {

    private static final String[] cacheNames = {"ClientAuthorization", "Client", "ClientRole", "DocumentationRequest",
            "DocumentationInstruction", "DocumentationLifecycle", "GeoHierarchy", "FunctionalUnit", "Location", "Message",
            "MessageSpecification", "OrganizationPlace", "PlannedShipment", "SecurityOrganization", "TransportUnitPoint",
            "Users", "UserRole", "UserAuthorization"};

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("mysqlPU");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        em.setPackagesToScan(new String[]{"com.hlag.fis.db.mysql", "com.hlag.fis.batch.model"});
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
        return entityManagerFactoryBean().getObject().unwrap(SessionFactory.class);
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
}*/