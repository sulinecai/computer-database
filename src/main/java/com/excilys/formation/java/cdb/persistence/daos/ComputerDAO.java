package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.Datasource;

public class ComputerDAO {

    private static final String SQL_SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, "
            + "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.id";

    private static final String SQL_SELECT_ALL_BY_PAGE = "SELECT computer.id, computer.name, introduced, discontinued, "
            + "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id "
            + "ORDER BY computer.id LIMIT ? OFFSET ?";

    private static final String SQL_SELECT_WITH_ID = "SELECT computer.id, computer.name, introduced, discontinued, "
            + "company_id, company.name AS company_name FROM computer LEFT JOIN company ON "
            + "company_id = company.id WHERE computer.id = ? ";

    private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) "
            + "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
            + "WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    
    private static final String SQL_SELECT_WITH_NAME_BY_PAGE = "SELECT computer.id, computer.name, introduced, discontinued, "
    		+ "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id "
    		+ "WHERE computer.name LIKE '?%' OR company.name LIKE '?%' LIMIT ? OFFSET ?";

    private static ComputerDAO computerDAO;

    private Connection connect = Datasource.getInstance();

    /**
     * Private constructor of ComputerDAO.
     */
    private ComputerDAO() {
    }

    /**
     * Instance of the singleton ComputerDAO.
     * @return the instance of ComputerDAO
     */
    public static synchronized ComputerDAO getInstance() {
        if (computerDAO == null) {
            computerDAO = new ComputerDAO();
        }
        return computerDAO;
    }

    public List<Computer> getAll() {
        List<Computer> computerList = new ArrayList<Computer>();

        try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Computer computer = ComputerMapper.convert(resultSet);
                computerList.add(computer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * Get all the computers on a given page.
     * @param page
     * @return list of the computers
     */
    public List<Computer> getAllByPage(Page page) {
        List<Computer> computerList = new ArrayList<Computer>();

        if (page.getCurrentPage() > 0) {

            try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL_BY_PAGE)) {
                statement.setInt(1, page.getMaxLine());
                statement.setInt(2, page.getPageFirstLine());

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Computer computer = ComputerMapper.convert(resultSet);
                    computerList.add(computer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return computerList;
    }

    public Optional<Computer> findById(Long id) {
        Optional<Computer> result = Optional.empty();
        if (id != null) {
            try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_WITH_ID)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    result = Optional.ofNullable(ComputerMapper.convert(resultSet));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public List<Computer> findByNameByPage(String name, Page page) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (page.getCurrentPage() > 0 && name != null && !name.isEmpty() && !name.contains("%")) {

            try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_WITH_NAME_BY_PAGE)) {
                statement.setString(1, name);
                statement.setInt(1, page.getMaxLine());
                statement.setInt(2, page.getPageFirstLine());

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Computer computer = ComputerMapper.convert(resultSet);
                    computerList.add(computer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return computerList;
    }

    /**
     * Add a computer in the database.
     * @param computer
     */
    public void create(Computer computer) {
        if (computer != null) {
            try (PreparedStatement statement = connect.prepareStatement(SQL_INSERT)) {
                statement.setString(1, computer.getName());
                Timestamp introDate = computer.getIntroducedDate() == null ? null
                        : Timestamp.valueOf(computer.getIntroducedDate().atStartOfDay());
                statement.setTimestamp(2, introDate);
                Timestamp discontDate = computer.getDiscontinuedDate() == null ? null
                        : Timestamp.valueOf(computer.getDiscontinuedDate().atStartOfDay());
                statement.setTimestamp(3, discontDate);
                if (computer.getCompany() == null) {
                    statement.setNull(4, java.sql.Types.INTEGER);
                } else {
                    statement.setLong(4, computer.getCompany().getIdCompany());
                }
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update an existing Computer.
     * @param computer
     */
    public void update(Computer computer) {
        try (PreparedStatement statement = connect.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, computer.getName());
            Timestamp introDate = computer.getIntroducedDate() == null ? null
                    : Timestamp.valueOf(computer.getIntroducedDate().atStartOfDay());
            statement.setTimestamp(2, introDate);
            Timestamp discontDate = computer.getDiscontinuedDate() == null ? null
                    : Timestamp.valueOf(computer.getDiscontinuedDate().atStartOfDay());
            statement.setTimestamp(3, discontDate);
            if (computer.getCompany() == null) {
                statement.setNull(4, java.sql.Types.INTEGER);
            } else {
                statement.setLong(4, computer.getCompany().getIdCompany());
            }
            statement.setLong(5, computer.getIdComputer());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an existing Computer.
     * @param id
     */
    public void delete(Long id) {
        try (PreparedStatement statement = connect.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
