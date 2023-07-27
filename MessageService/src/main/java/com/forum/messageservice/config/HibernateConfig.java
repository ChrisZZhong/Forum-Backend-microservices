package com.forum.messageservice.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Autowired
    HibernateProperty hibernateProperty;

    @Bean
    protected LocalSessionFactoryBean sessionFactory() {
        System.out.println("getting session factory");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.forum.messageservice.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());
        System.out.println("getting session finished");
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(hibernateProperty.getDriver());
        dataSource.setUrl(hibernateProperty.getUrl());
        dataSource.setUsername(hibernateProperty.getUsername());
        dataSource.setPassword(hibernateProperty.getPassword());
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", hibernateProperty.getShowsql());
        hibernateProperties.setProperty("hibernate.dialect", hibernateProperty.getDialect());
        return hibernateProperties;
    }
}