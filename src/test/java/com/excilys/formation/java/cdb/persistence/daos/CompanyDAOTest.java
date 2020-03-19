package com.excilys.formation.java.cdb.persistence.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class CompanyDAOTest {

    @Before
    public void resetSingleton()
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = CompanyDAO.class.getDeclaredField("companyDAO");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testGetInstance() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        assertNotNull(companyDAO);
        assertEquals("the two instance are not equals (singleton)", CompanyDAO.getInstance(), companyDAO);
    }

//    @Test
//    public void testGetAll() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testGetAllByPage() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testFindByIdLong() {
//        fail("Not yet implemented");
//    }

}
