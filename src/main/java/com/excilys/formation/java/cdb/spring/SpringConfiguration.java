package com.excilys.formation.java.cdb.spring;

import java.sql.Connection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.formation.java.cdb.persistence.Datasource;

@Configuration
@ComponentScan({"com.excilys.formation.java.cdb.services", "com.excilys.formation.java.cdb.persistence.daos"})
public class SpringConfiguration {

    public static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    @Bean
    public Connection getConnection() {
    return Datasource.getInstance();
    }
}