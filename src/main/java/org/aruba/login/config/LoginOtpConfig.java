package org.aruba.login.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "loginEntityManagerFactory", transactionManagerRef = "loginTransactionManager", basePackages = {
    "org.aruba.login.repository"})
public class LoginOtpConfig
{
    @Bean(name = "loginDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasourceConfiguration()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "loginEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder,
        final @Qualifier("loginDataSource") DataSource dataSource)
    {
        return builder.dataSource(dataSource).packages("org.aruba.login.domain").persistenceUnit("db-login").build();
    }

    @Bean(name = "loginTransactionManager")
    public PlatformTransactionManager customerTransactionManager(
        final @Qualifier("loginEntityManagerFactory") EntityManagerFactory peopleEntityManagerFactory)
    {
        return new JpaTransactionManager(peopleEntityManagerFactory);
    }
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // configurazioni dell'oggetto ObjectMapper (ad esempio, formato data/ora)
        return objectMapper;
    }
}
