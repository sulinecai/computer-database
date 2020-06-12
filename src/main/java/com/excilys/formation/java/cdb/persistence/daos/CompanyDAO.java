package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.Datasource;

public class CompanyDAO {

    private static final String SQL_SELECT_ALL = "SELECT id, name FROM company ORDER BY id";

    private static final String SQL_SELECT_ALL_BY_PAGE = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";

    private static final String SQL_SELECT_WITH_ID = "SELECT id, name FROM company WHERE id = ?";

    private static CompanyDAO companyDAO;

    private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private Connection connect = Datasource.getInstance();

    /**
     * Private constructor of CompanyDAO.
     */
    private CompanyDAO() {
    }

    /**
     * Instance of the singleton CompanyDAO.
     * @return the instance of CompanyDAO
     */
    public static synchronized CompanyDAO getInstance() {
        if (companyDAO == null) {
            companyDAO = new CompanyDAO();
        }
        return companyDAO;
    }

    public List<Company> getAll() {
        List<Company> companyList = new ArrayList<Company>();

        try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = CompanyMapper.convert(resultSet);
                companyList.add(company);
            }
        } catch (SQLException e) {
            logger.error("sql error when listing all companies", e);
        }
        return companyList;
    }

    /**
     * Get all the companies on a given page.
     * @param page
     * @return list of the companies
     */
    public List<Company> getAllByPage(Page page) {
        List<Company> companyList = new ArrayList<Company>();

        if (page.getCurrentPage() > 0) {
            try (PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL_BY_PAGE)) {
                statement.setInt(1, page.getMaxLine());
                statement.setInt(2, page.getPageFirstLine());

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Company company = CompanyMapper.convert(resultSet);
                    companyList.add(company);
                }
            } catch (SQLException e) {
                logger.error("sql error when listing companies by page", e);
            }
        }

        return companyList;
    }

    public Optional<Company> findById(Long id) {
        Optional<Company> result = Optional.empty();
        if (id != null) {
            try (PreparedStatement statement = this.connect.prepareStatement(SQL_SELECT_WITH_ID)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result = Optional.ofNullable(CompanyMapper.convert(resultSet));
                }
            } catch (SQLException e) {
                logger.error("sql error when finding company with id", e);
            }
        }
        return result;
    }
}
