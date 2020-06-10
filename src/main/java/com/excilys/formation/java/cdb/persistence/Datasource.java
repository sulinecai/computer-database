package com.excilys.formation.java.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class Datasource {

    private static Connection connection;
    private HikariDataSource dataSource;

    private Logger logger = LoggerFactory.getLogger(Datasource.class);

    private Datasource() {
        try {
        	HikariConfig config = new HikariConfig("/datasource.properties");
        	dataSource = new HikariDataSource(config);
        	
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("error when connecting to datasource", e);
        }
    }

    public static synchronized Connection getInstance() {
        if (connection == null) {
            new Datasource();
        }
        return connection;
    }  
}
