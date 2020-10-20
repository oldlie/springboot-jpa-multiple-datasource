package com.oldlie.learning.doubledatabaseresources;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author CL
 * @date 2020/10/20
 */
@Configuration
public class DataSourceConfig {

    public static final String DATA_SOURCE_DATABASE1 = "database1DataSource";
    public static final String DATA_SOURCE_DATABASE2 = "database2DataSource";

    @Bean(name = DATA_SOURCE_DATABASE1)
    @Qualifier(DATA_SOURCE_DATABASE1)
    @ConfigurationProperties(prefix = "spring.datasource.database1")
    @Primary
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DATA_SOURCE_DATABASE2)
    @Qualifier(DATA_SOURCE_DATABASE2)
    @ConfigurationProperties(prefix = "spring.datasource.database2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }
}
