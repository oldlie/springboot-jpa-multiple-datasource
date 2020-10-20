package com.oldlie.learning.doubledatabaseresources;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CL
 * @date 2020/10/20
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = Database1Config.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = Database1Config.TRANSACTION_MANAGER,
        basePackages = { Database1Config.SCAN_PACKAGES  }
)
public class Database1Config {

    public static final String SCAN_PACKAGES = "com.oldlie.learning.doubledatabaseresources.database1";
    public static final String ENTITY_MANAGER = "entityManagerDatabase1";
    public static final String ENTITY_MANAGER_FACTORY = "entityManagerFactoryDatabase1";
    public static final String TRANSACTION_MANAGER = "transactionManagerDatabase1";

    @Autowired
    @Qualifier(DataSourceConfig.DATA_SOURCE_DATABASE1)
    private DataSource dataSource1;

    @Primary
    @Bean(name = ENTITY_MANAGER)
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryDatasource(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDatasource(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(this.dataSource1)
                .properties(getVendorProperties(this.dataSource1))
                .packages(Database1Config.SCAN_PACKAGES)
                .persistenceUnit("database1PersistenceUnit")
                .build();
    }

    @Autowired
    private HibernateProperties hibernateProperties;
    @Autowired
    private JpaProperties jpaProperties;

    private Map<String, Object> getVendorProperties(DataSource dataSource) {
        Map<String, String> map = new HashMap<>(256);
        // 这个不知道对应的是啥
        // map.put("spring.jpa.database", "mysql");

        map.put(AvailableSettings.SHOW_SQL, "true");
        map.put(AvailableSettings.HBM2DDL_AUTO, "update");
        map.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        return hibernateProperties.determineHibernateProperties(map,
                new HibernateSettings());
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManagerDatabase1(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryDatasource(builder).getObject());
    }
}
