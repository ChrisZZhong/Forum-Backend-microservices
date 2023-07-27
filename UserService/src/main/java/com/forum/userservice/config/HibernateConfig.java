package com.forum.userservice.config;

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
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    HibernateProperty hibernateProperty;

    @Bean
    protected LocalSessionFactoryBean sessionFactory() {
        System.out.println("getting session factory");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.forum.userservice.domain");
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

    @Bean
    @Autowired
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", hibernateProperty.getShowsql());
        hibernateProperties.setProperty("hibernate.dialect", hibernateProperty.getDialect());
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hibernateProperty.getAuto());
//        hibernateProperties.setProperty("hibernate.current_session_context_class", hibernateProperty.getThread());
        return hibernateProperties;
    }
    
//    @Bean
//    @Autowired
//    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
}
