package com.excilys.formation.java.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;

public final class MysqlConnect {

    private static Connection connection;

    private Logger logger = LoggerFactory.getLogger(MysqlConnect.class);

    private MysqlConnect() {
        try {
            Properties properties = new Properties();
            InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties");
            properties.load(in);
            in.close();
            String url = properties.getProperty("jdbc.url");
            String driver = properties.getProperty("jdbc.driver");
            String userName = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            logger.error("error when connecting to db", e);
        }
    }

    public static synchronized Connection getInstance() {
        if (connection == null) {
            new MysqlConnect();
        }
        return connection;
    }

}
