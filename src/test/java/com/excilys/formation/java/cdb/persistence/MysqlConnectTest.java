package com.excilys.formation.java.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class MysqlConnectTest {

    @Before
    public void resetSingleton()
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = MysqlConnect.class.getDeclaredField("connection");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testGetInstance() {
        Connection connection = MysqlConnect.getInstance();
        assertNotNull(connection);
        assertEquals("the two instance are not equals (singleton)", MysqlConnect.getInstance(), connection);
    }
}
