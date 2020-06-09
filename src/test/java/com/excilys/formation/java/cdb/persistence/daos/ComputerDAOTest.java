package com.excilys.formation.java.cdb.persistence.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.MysqlConnect;

public class ComputerDAOTest {

    /**
     * Reset the connection.
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Before
    public void setUp()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field instance = MysqlConnect.class.getDeclaredField("connection");
        instance.setAccessible(true);
        instance.set(null, null);
        Field daoInstance = ComputerDAO.class.getDeclaredField("computerDAO");
        daoInstance.setAccessible(true);
        daoInstance.set(null, null);
    }

    /**
     * Test the singleton pattern of the instance ComputerDAO.
     */
    @Test
    public void testGetInstance() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        assertNotNull(computerDAO);
        assertEquals("The two instance are different (not singleton).", ComputerDAO.getInstance(), computerDAO);
    }

    /**
     * Test the method getAll.
     */
    @Test
    public void testGetAll() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        List<Computer> computers = computerDAO.getAll();
        assertFalse(computers.isEmpty());
        assertEquals(50, computers.size());
    }

    /**
     * Test the first page of the method getAllbyPage.
     */
    @Test
    public void testGetAllByPageFirstPage() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, 1));
        assertFalse(computers.isEmpty());
        assertEquals(8, computers.size());
        assertEquals(1L, computers.get(0).getIdComputer().longValue());
    }

    /**
     * Test the second page of the method getAllbyPage.
     */
    @Test
    public void testGetAllByPageSecondPage() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, 2));
        assertFalse(computers.isEmpty());
        assertEquals(8, computers.size());
        assertEquals(9L, computers.get(0).getIdComputer().longValue());
    }

    /**
     * Test that the page is empty when the page number is negative.
     */
    @Test
    public void testGetAllByPageNegative() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, -3));
        assertTrue(computers.isEmpty());
    }

    /**
     * Test that the page is empty when when the page number is 0.
     */
    @Test
    public void testGetAllByPageZero() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, 0));
        assertTrue(computers.isEmpty());
    }

    /**
     * Test that the page is empty when the page number exceed the total number of
     * pages.
     */
    @Test
    public void testGetAllByPageExceeded() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, 8));
        assertTrue(computers.isEmpty());
    }

    /**
     * Test that the method findById with an existing id returns the correct
     * computer.
     */
    @Test
    public void testFindExistingId() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        Optional<Computer> optComputer = computerDAO.findById(2L);
        assertTrue(optComputer.isPresent());
        assertEquals(2L, optComputer.get().getIdComputer(), 0);
        assertEquals("CM-2a", optComputer.get().getName());
    }

    /**
     * Test that the method findById with not existing id returns an empty optional.
     */
    @Test
    public void testFindIdNotExisting() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        Optional<Computer> optComputer = computerDAO.findById(55L);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with id null returns an empty optional.
     */
    @Test
    public void testFindIdNull() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        Optional<Computer> optComputer = computerDAO.findById(null);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with a negative id returns an empty optional.
     */
    @Test
    public void testFindNegativeId() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        Optional<Computer> optComputer = computerDAO.findById(-1L);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with an id equals to 0 returns an empty
     * optional.
     */
    @Test
    public void testFindIdZero() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        Optional<Computer> optComputer = computerDAO.findById(0L);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with an id equals to 0 returns an empty
     * optional.
     */
    @Test
    public void testCreate() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        Computer computer = new Computer("computer Test", LocalDate.of(2010, 10, 5), LocalDate.of(2011, 3, 15),
                new Company(1L));
        List<Computer> computers = computerDAO.getAll();
        assertEquals(50, computers.size());
        computerDAO.create(computer);
        computers = computerDAO.getAll();
        assertEquals(51, computers.size());
    }


    @Test
    public void testUpdate() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        Computer computer = new Computer(1L, "computer Test 1", LocalDate.of(2010, 10, 5), LocalDate.of(2011, 3, 15),
                new Company(1L));
        computerDAO.update(computer);
        assertEquals("computer Test 1", computerDAO.findById(1L).get().getName());
    }

    @Test
    public void testDelete() {
        ComputerDAO computerDAO = ComputerDAO.getInstance();
        assertTrue(computerDAO.findById(1L).isPresent());
        computerDAO.delete(1L);
        assertFalse(computerDAO.findById(1L).isPresent());
    }
}
