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

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;

public class CompanyDAOTest {

    /**
     * Reset the singleton of companyDAO.
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Before
    public void setUp()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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
        assertEquals(1L, companies.get(0).getIdCompany().longValue());
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
        assertEquals(9L, companies.get(0).getIdCompany().longValue());
    }

    /**
     * Test that the first page is returned when the page number is negative.
     */
    @Test
    public void testGetAllByPageNegative() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        List<Company> companies = companyDAO.getAllByPage(new Page(8, -3));
        assertTrue(companies.isEmpty());
    }

    /**
     * Test that the first page is returned when the page number is negative.
     */
    @Test
    public void testGetAllByPageZero() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        List<Company> companies = companyDAO.getAllByPage(new Page(8, 0));
        assertTrue(companies.isEmpty());
    }

    /**
     * Test that the page is empty when the page number exceed the total number of
     * pages.
     */
    @Test
    public void testGetAllByPageExceed() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        List<Company> companies = companyDAO.getAllByPage(new Page(8, 3));
        assertTrue(companies.isEmpty());
    }

    /**
     * Test that the method findById with an existing id returns the correct
     * company.
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

    /**
     * Test that the method findById with a negative id returns an empty optional.
     */
    @Test
    public void testFindNegativeId() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        Optional<Company> optCompany = companyDAO.findById(-1L);
        assertFalse(optCompany.isPresent());
    }

    /**
     * Test that the method findById with an id equals to 0 returns an empty
     * optional.
     */
    @Test
    public void testFindIdZero() {
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        Optional<Company> optCompany = companyDAO.findById(0L);
        assertFalse(optCompany.isPresent());
    }

}
