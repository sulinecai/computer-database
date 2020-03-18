package com.excilys.formation.java.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

public class MysqlConnectTest {

	@Test
	public void testGetInstance() {
		Connection connection = MysqlConnect.getInstance();
		assertNotNull(connection);
		assertEquals(MysqlConnect.getInstance(), connection);
	}
}
