package com.excilys.formation.java.cdb.spring;

import java.sql.Connection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.formation.java.cdb.persistence.Datasource;
import com.excilys.formation.java.cdb.services.ComputerService;

@Configuration
@ComponentScan("com.excilys.formation.java.cdb")
public class SpringConfiguration {

    public static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    @Bean
    public ComputerService computerService() {
    return ComputerService.getInstance();
    }

    @Bean
    public Connection getConnection() {
    return Datasource.getInstance();
    }
}