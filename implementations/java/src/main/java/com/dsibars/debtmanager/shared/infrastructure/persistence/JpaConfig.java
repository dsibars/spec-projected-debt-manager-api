package com.dsibars.debtmanager.shared.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.dsibars.debtmanager",
        entityManagerFactoryRef = "writeEntityManagerFactory",
        transactionManagerRef = "writeTransactionManager"
)
public class JpaConfig {

    @Primary
    @Bean(name = "writeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean writeEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("writeDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");

        return builder
                .dataSource(dataSource)
                .packages("com.dsibars.debtmanager")
                .persistenceUnit("write")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "writeTransactionManager")
    public PlatformTransactionManager writeTransactionManager(
            @Qualifier("writeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "readEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean readEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("readDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");

        return builder
                .dataSource(dataSource)
                .packages("com.dsibars.debtmanager")
                .persistenceUnit("read")
                .properties(properties)
                .build();
    }

    @Bean(name = "readTransactionManager")
    public PlatformTransactionManager readTransactionManager(
            @Qualifier("readEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
