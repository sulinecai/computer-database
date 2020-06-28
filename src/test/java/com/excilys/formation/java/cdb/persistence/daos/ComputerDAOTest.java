package com.excilys.formation.java.cdb.persistence.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.spring.SpringConfiguration;

public class ComputerDAOTest {

    private ComputerDAO computerDAO;

    /**
     * Reset the connection.
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Before
    public void setUp() throws InvocationTargetException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Constructor<ComputerDAO> computerDAOConstructor = ComputerDAO.class.getDeclaredConstructor();
        assertEquals(computerDAOConstructor.isAccessible(), false);
        computerDAOConstructor.setAccessible(true);
        computerDAO = computerDAOConstructor.newInstance();
        ReflectionTestUtils.setField(computerDAO, "jdbcTemplate", new SpringConfiguration().getJdbcTemplate());
    }

    /**
     * Test the method getAll.
     */
    @Test
    public void testGetNumberComputers() {
        int nbComputers = computerDAO.getNumberComputers();
        assertEquals(50, nbComputers);
    }

    /**
     * Test the first page of the method getAllbyPage.
     */
    @Test
    public void testGetAllByPageFirstPage() {
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
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, -3));
        assertTrue(computers.isEmpty());
    }

    /**
     * Test that the page is empty when when the page number is 0.
     */
    @Test
    public void testGetAllByPageZero() {
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, 0));
        assertTrue(computers.isEmpty());
    }

    /**
     * Test that the page is empty when the page number exceed the total number of
     * pages.
     */
    @Test
    public void testGetAllByPageExceeded() {
        List<Computer> computers = computerDAO.getAllByPage(new Page(8, 8));
        assertTrue(computers.isEmpty());
    }

    /**
     * Test that the method findById with an existing id returns the correct
     * computer.
     */
    @Test
    public void testFindExistingId() {
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
        Optional<Computer> optComputer = computerDAO.findById(55L);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with id null returns an empty optional.
     */
    @Test
    public void testFindIdNull() {
        Optional<Computer> optComputer = computerDAO.findById(null);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with a negative id returns an empty optional.
     */
    @Test
    public void testFindNegativeId() {
        Optional<Computer> optComputer = computerDAO.findById(-1L);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with an id equals to 0 returns an empty
     * optional.
     */
    @Test
    public void testFindIdZero() {
        Optional<Computer> optComputer = computerDAO.findById(0L);
        assertFalse(optComputer.isPresent());
    }

    /**
     * Test that the method findById with an id equals to 0 returns an empty
     * optional.
     */
    @Test
    public void testCreate() {
        Computer computer = new Computer.Builder()
                .setName("computer Test")
                .setIntroducedDate(LocalDate.of(2010, 10, 5))
                .setDiscontinuedDate(LocalDate.of(2011, 3, 15))
                .setCompany(new Company.Builder()
                        .setIdCompany(1L).build()).build();
        assertEquals(50, computerDAO.getNumberComputers());
        computerDAO.create(computer);
        assertEquals(51, computerDAO.getNumberComputers());
    }


    @Test
    public void testUpdate() {
        Computer computer = new Computer.Builder()
                .setIdComputer(1L)
                .setName("computer Test 1")
                .setIntroducedDate(LocalDate.of(2010, 10, 5))
                .setDiscontinuedDate(LocalDate.of(2011, 3, 15))
                .setCompany(new Company.Builder()
                        .setIdCompany(1L).build()).build();
        computerDAO.update(computer);
        assertEquals("computer Test 1", computerDAO.findById(1L).get().getName());
    }

    @Test
    public void testDelete() {
        assertTrue(computerDAO.findById(5L).isPresent());
        computerDAO.delete(5L);
        assertFalse(computerDAO.findById(5L).isPresent());
    }

    @Test
    public void testOrderByComputerNameAsc() {
        Page page = new Page(60, 1);
        List<Computer> allComputers = computerDAO.getAllByPage(page);
        List<Computer> orderedComputers = computerDAO.orderBy(page, "computerAsc");
        Comparator<Computer> byComputerNameAsc = Comparator.comparing(Computer::getName);
        allComputers.sort(byComputerNameAsc);
        assertEquals(allComputers, orderedComputers);
    }
}
