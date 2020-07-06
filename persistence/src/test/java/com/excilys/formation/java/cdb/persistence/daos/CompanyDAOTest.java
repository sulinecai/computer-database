package com.excilys.formation.java.cdb.persistence.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.spring.HibernateConfig;

public class CompanyDAOTest {

    private CompanyDAO companyDAO;
    private ComputerDAO computerDAO;

    private ApplicationContext context;
    private SessionFactory sessionFactory;

    /**
     * Reset the singleton of companyDAO.
     */
    @Before
    public void setup() {
        context  = new AnnotationConfigApplicationContext(HibernateConfig.class);
        sessionFactory = context.getBean(SessionFactory.class);
        companyDAO = new CompanyDAO(sessionFactory);
        computerDAO = new ComputerDAO(sessionFactory);
    }


    /**
     * Test the method getAll.
     */
    @Test
    public void testGetAll() {
        List<Company> companies = companyDAO.getAll();
        assertFalse(companies.isEmpty());
    }

    /**
     * Test the first page of the method getAllbyPage.
     */
    @Test
    public void testGetAllByPageFirstPage() {
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
        List<Company> companies = companyDAO.getAllByPage(new Page(8, 2));
        assertFalse(companies.isEmpty());
        assertEquals(2, companies.size());
        assertEquals(9L, companies.get(0).getIdCompany().longValue());
    }

    /**
     * Test that the page is empty when the page number is negative.
     */
    @Test
    public void testGetAllByPageNegative() {
        List<Company> companies = companyDAO.getAllByPage(new Page(8, -3));
        assertTrue(companies.isEmpty());
    }

    /**
     * Test that the page is empty when  when the page number is 0.
     */
    @Test
    public void testGetAllByPageZero() {
        List<Company> companies = companyDAO.getAllByPage(new Page(8, 0));
        assertTrue(companies.isEmpty());
    }

    /**
     * Test that the page is empty when the page number exceed the total number of
     * pages.
     */
    @Test
    public void testGetAllByPageExceeded() {
        List<Company> companies = companyDAO.getAllByPage(new Page(8, 3));
        assertTrue(companies.isEmpty());
    }

    /**
     * Test that the method findById with an existing id returns the correct
     * company.
     */
    @Test
    public void testFindExistingId() {
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
        Optional<Company> optCompany = companyDAO.findById(15L);
        assertFalse(optCompany.isPresent());
    }

    /**
     * Test that the method findById with id null returns an empty optional.
     */
    @Test
    public void testFindIdNull() {
        Optional<Company> optCompany = companyDAO.findById(null);
        assertFalse(optCompany.isPresent());
    }

    /**
     * Test that the method findById with a negative id returns an empty optional.
     */
    @Test
    public void testFindNegativeId() {
        Optional<Company> optCompany = companyDAO.findById(-1L);
        assertFalse(optCompany.isPresent());
    }

    /**
     * Test that the method findById with an id equals to 0 returns an empty
     * optional.
     */
    @Test
    public void testFindIdZero() {
        Optional<Company> optCompany = companyDAO.findById(0L);
        assertFalse(optCompany.isPresent());
    }

    /**
     * Test that the company and all its computers are deleted.
     */
    @Test
    public void testDelete() {
        assertTrue(companyDAO.findById(1L).isPresent());
        Page page = new Page(60, 1);
        assertEquals(50, computerDAO.getAllByPage(page).size());
        companyDAO.delete(1L);
        assertFalse(companyDAO.findById(1L).isPresent());
        assertEquals(32, computerDAO.getAllByPage(page).size());
    }

    @After
    public void closeContext() {
        ((ConfigurableApplicationContext) context).close();
    }
}
