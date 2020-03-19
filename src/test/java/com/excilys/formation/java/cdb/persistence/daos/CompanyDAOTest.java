package com.excilys.formation.java.cdb.persistence.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.MysqlConnect;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MysqlConnect.class, CompanyMapper.class })
public class CompanyDAOTest {

    /**
     * Reset the singleton of companyDAO.
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Before
    public void resetSingleton() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field instance = CompanyDAO.class.getDeclaredField("companyDAO");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    /**
     * Test the singleton pattern of the instance CompanyDAO.
     */
    @Test
    public void testGetInstance() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        assertNotNull(companyDAO);
        assertEquals("The two instance are different (not singleton).", CompanyDAO.getInstance(), companyDAO);
    }

    /**
     * Test the method getAll.
     */
    @Test
    public void testGetAll() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        List<Company> companies = companyDAO.getAll();
        assertFalse(companies.isEmpty());
        assertEquals(10, companies.size());
    }

    /**
     * Test the first page of the method getAllbyPage.
     */
    @Test
    public void testGetAllByPageFirstPage() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        List<Company> companies = companyDAO.getAllByPage(new Page(8, 1));
        assertFalse(companies.isEmpty());
        assertEquals(8, companies.size());
        assertEquals(1L, companies.get(0).getIdCompany(), 0);
    }

    /**
     * Test the second page of the method getAllbyPage.
     */
    @Test
    public void testGetAllByPageSecondPage() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        List<Company> companies = companyDAO.getAllByPage(new Page(8, 2));
        assertFalse(companies.isEmpty());
        assertEquals(2, companies.size());
        assertEquals(9L, companies.get(0).getIdCompany(), 0);
    }

    /**
     * Test that the method findById with an existing id returns the correct company.
     */
    @Test
    public void testFindExistingId() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        Optional<Company> optCompany = companyDAO.findById(2L);
        assertTrue(optCompany.isPresent());
        assertEquals(2L, optCompany.get().getIdCompany(), 0);
        assertEquals("Thinking Machines", optCompany.get().getName());
    }

    /**
     * Test that the method findById with not existing id returns an empty optional.
     */
    @Test
    public void testFindIdNotExisting() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        Optional<Company> optCompany = companyDAO.findById(15L);
        assertFalse(optCompany.isPresent());
    }

    /**
     * Test that the method findById with id null returns an empty optional.
     */
    @Test
    public void testFindIdNull() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        Optional<Company> optCompany = companyDAO.findById(null);
        assertFalse(optCompany.isPresent());
    }

//    @Test
//    public void testGetAll() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
//        CompanyDAO companyDAO = CompanyDAO.getInstance();
//        PowerMockito.mockStatic(MysqlConnect.class);
//        PowerMockito.mockStatic(CompanyMapper.class);
//        when(MysqlConnect.getInstance()).thenReturn(conn);
//        try {
//            when(conn.prepareStatement(SQL_SELECT_ALL)).thenReturn(statement);
//            when(statement.executeQuery()).thenReturn(resultSet);
//            when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//            when(CompanyMapper.convert(resultSet))
//                .thenReturn(new Company(1L, "company1"))
//                .thenReturn(new Company(2L, "company2"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        List<Company> companies = companyDAO.getAll();
//        assertFalse(companies.isEmpty());
//        assertEquals(2, companies.size());
//    }

}
