package com.excilys.formation.java.cdb.spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.formation.java.cdb.services", "com.excilys.formation.java.cdb.persistence.daos",
        "com.excilys.formation.java.cdb.controllers" })
public class SpringConfiguration {

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        HikariConfig config = new HikariConfig("/datasource.properties");
        DataSource datasource = new HikariDataSource(config);
        return new JdbcTemplate(datasource);
    }
}