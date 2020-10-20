package com.oldlie.learning.doubledatabaseresources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private JpaProperties jpaProperties;

    private Map<String, String> getVendorProperties(DataSource dataSource) {
       //   this.jpaProperties.setDatabase(Database.MYSQL);
       //   this.jpaProperties.setShowSql(true);
       //   this.jpaProperties.setGenerateDdl(true);
       //   this.jpaProperties.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return this.jpaProperties.getProperties();
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManagerDatabase1(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryDatasource(builder).getObject());
    }
}
