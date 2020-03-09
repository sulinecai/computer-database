package com.excilys.formation.java.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public final class MysqlConnect {
	
    private String url= "jdbc:mysql://localhost:3306/";
    private String dbName = "computer-database-db";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String userName = "admincdb";
    private String password = "qwerty1234";
    
    private static Connection connection;

    
    private MysqlConnect() {
        try {
			Class.forName(driver);       
			connection = DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getInstance() {
        if ( connection == null ) {
            new MysqlConnect();
        }
        return connection;
    }
    
}
