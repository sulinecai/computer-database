package com.excilys.formation.java.cdb.spring;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.formation.java.cdb.services", "com.excilys.formation.java.cdb.persistence.daos"})
public class SpringConfiguration {

    public static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    @Bean
    public JdbcTemplate getJdbcTemlplate() {
        HikariConfig config = new HikariConfig("/datasource.properties");
        DataSource datasource = new HikariDataSource(config);
        return new JdbcTemplate(datasource);
    }

}