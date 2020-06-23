package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.Datasource;

@Repository
public class ComputerDAO {

    private static final String SQL_SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, "
            + "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id";

    private static final String SQL_COUNT_ALL = "SELECT COUNT(id) AS total FROM computer";

    private static final String SQL_SELECT_WITH_ID = "SELECT computer.id, computer.name, introduced, discontinued, "
            + "company_id, company.name AS company_name FROM computer LEFT JOIN company ON "
            + "company_id = company.id WHERE computer.id = ? ";

    private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) "
            + "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
            + "WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";

    private static final String SQL_SELECT_WITH_NAME = "SELECT computer.id, computer.name, introduced, discontinued, "
            + "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id "
            + "WHERE computer.name LIKE ? OR company.name LIKE ?";

    private static final String SQL_ORDER_BY_COMPUTER  = " ORDER BY computer.name";

    private static final String SQL_ORDER_BY_COMPANY  = " ORDER BY company_name";

    private static final String SQL_DESC  = " DESC";

    private static final String SQL_ASC  = " ASC";

    private static final String SQL_OFFSET  = " LIMIT ? OFFSET ?";

    @Autowired
    private Connection connect;

    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Private constructor of ComputerDAO.
     */
    private ComputerDAO() {
    }

    public int getNumberComputers() {
        int count = -1;
        try (PreparedStatement statement = Datasource.getInstance().prepareStatement(SQL_COUNT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            logger.error("sql error when the total number of computers", e);
        }
        return count;
    }

    /**
     * Get all the computers on a given page.
     *
     * @param page
     * @return list of the computers
     */
    public List<Computer> getAllByPage(Page page) {
        List<Computer> computerList = new ArrayList<Computer>();

        if (page == null) {
            logger.error("the page is null");
        } else if (page.getCurrentPage() > 0) {
            try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL.concat(SQL_OFFSET))) {
                statement.setInt(1, page.getMaxLine());
                statement.setInt(2, page.getPageFirstLine());

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Computer computer = ComputerMapper.convert(resultSet);
                    computerList.add(computer);
                }
            } catch (SQLException e) {
                logger.error("sql error when listing all companies by page", e);
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
                logger.error("sql error when finding computer with id", e);
            }
        } else {
            logger.error("company id to find is null");
        }
        return result;
    }

    public List<Computer> findByNameByPage(String name, Page page) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (page.getCurrentPage() > 0 && name != null && !name.isEmpty() && !name.contains("%") && !name.contains("_")) {

            try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_WITH_NAME.concat(SQL_OFFSET))) {
                statement.setString(1, "%".concat(name).concat("%"));
                statement.setString(2, "%".concat(name).concat("%"));
                statement.setInt(3, page.getMaxLine());
                statement.setInt(4, page.getPageFirstLine());

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Computer computer = ComputerMapper.convert(resultSet);
                    computerList.add(computer);
                }
            } catch (SQLException e) {
                logger.error("sql error when finding computer with name", e);
            }
        }
        return computerList;
    }

    public List<Computer> findAllByName(String name) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (name != null && !name.isEmpty() && !name.contains("%") && !name.contains("_")) {

            try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_WITH_NAME)) {
                statement.setString(1, "%".concat(name).concat("%"));
                statement.setString(2, "%".concat(name).concat("%"));
                System.out.println(statement);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Computer computer = ComputerMapper.convert(resultSet);
                    computerList.add(computer);
                }
            } catch (SQLException e) {
                logger.error("sql error when finding all computer with name", e);
            }
        }
        return computerList;
    }

    /**
     * Add a computer in the database.
     *
     * @param computer
     */
    public void create(Computer computer) {
        if (computer != null) {
            try (PreparedStatement statement = connect.prepareStatement(SQL_INSERT)) {
                statement.setString(1, computer.getName());
                statement.setTimestamp(2, localDateToTimestamp(computer.getIntroducedDate()));
                statement.setTimestamp(3, localDateToTimestamp(computer.getDiscontinuedDate()));
                if (computer.getCompany() == null) {
                    statement.setNull(4, java.sql.Types.INTEGER);
                } else {
                    statement.setLong(4, computer.getCompany().getIdCompany());
                }
                statement.execute();
            } catch (SQLException e) {
                logger.error("sql error when creating a computer", e);
            }
        } else {
            logger.error("the computer is null");
        }
    }

    /**
     * Update an existing Computer.
     *
     * @param computer
     */
    public void update(Computer computer) {
        if (computer != null) {
            try (PreparedStatement statement = connect.prepareStatement(SQL_UPDATE)) {
                statement.setString(1, computer.getName());
                statement.setTimestamp(2, localDateToTimestamp(computer.getIntroducedDate()));
                statement.setTimestamp(3, localDateToTimestamp(computer.getDiscontinuedDate()));
                if (computer.getCompany() == null) {
                    statement.setNull(4, java.sql.Types.INTEGER);
                } else {
                    statement.setLong(4, computer.getCompany().getIdCompany());
                }
                statement.setLong(5, computer.getIdComputer());
                statement.execute();
            } catch (SQLException e) {
                logger.error("sql exception", e);
            }
        } else {
            logger.error("the computer is null");
        }
    }

    /**
     * Delete an existing Computer.
     *
     * @param id
     */
    public void delete(Long id) {
        if (id != null) {
            try (PreparedStatement statement = connect.prepareStatement(SQL_DELETE)) {
                statement.setLong(1, id);
                statement.execute();
            } catch (SQLException e) {
                logger.error("sql exception", e);
            }
        } else {
            logger.error("the computer id to delete is null");
        }
    }

    public List<Computer> orderBy(Page page, String parameter) {
        List<Computer> computerList = new ArrayList<Computer>();

        String requete = SQL_SELECT_ALL;

        switch (parameter) {
        case "computerAsc":
            requete = requete.concat(SQL_ORDER_BY_COMPUTER).concat(SQL_ASC).concat(SQL_OFFSET);
            break;
        case "computerDesc":
            requete = requete.concat(SQL_ORDER_BY_COMPUTER).concat(SQL_DESC).concat(SQL_OFFSET);
            break;
        case "companyAsc":
            requete = requete.concat(SQL_ORDER_BY_COMPANY).concat(SQL_ASC).concat(SQL_OFFSET);
            break;
        case "companyDesc":
            requete = requete.concat(SQL_ORDER_BY_COMPANY).concat(SQL_DESC).concat(SQL_OFFSET);
            break;
        default:
            requete = requete.concat(SQL_OFFSET);
            break;
        }

        try (PreparedStatement statement = connect.prepareStatement(requete)) {
            statement.setInt(1, page.getMaxLine());
            statement.setInt(2, page.getPageFirstLine());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Computer computer = ComputerMapper.convert(resultSet);
                computerList.add(computer);
            }
        } catch (SQLException e) {
            logger.error("sql error when sorting computers", e);
        }
        return computerList;
    }

    private Timestamp localDateToTimestamp(LocalDate localDate) {
        Timestamp result = null;
        if (localDate != null) {
            if (localDate.isEqual(LocalDate.of(1970, 1, 1))) {
                result = Timestamp.valueOf(localDate.atStartOfDay().plusHours(1).plusSeconds(1));
            } else {
                result = Timestamp.valueOf(localDate.atStartOfDay());
            }
        }
        return result;
    }

}
