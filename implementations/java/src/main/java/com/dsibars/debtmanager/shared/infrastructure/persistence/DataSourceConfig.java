package com.dsibars.debtmanager.shared.infrastructure.persistence;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("db.write")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("db.write.hikari")
    public DataSource writeDataSource() {
        return writeDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @ConfigurationProperties("db.read")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("db.read.hikari")
    public DataSource readDataSource() {
        return readDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }
}
