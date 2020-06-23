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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;

@Repository
public class CompanyDAO {

    private static final String SQL_SELECT_ALL = "SELECT id, name FROM company ORDER BY id";

    private static final String SQL_SELECT_ALL_BY_PAGE = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";

    private static final String SQL_SELECT_WITH_ID = "SELECT id, name FROM company WHERE id = ?";

    private static final String SQL_DELETE_COMPANY_WITH_ID = "DELETE FROM company WHERE id = ?";

    private static final String SQL_DELETE_COMPUTER_WITH_COMPANY_ID = "DELETE FROM computer WHERE company_id = ?";

    private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    @Autowired
    private Connection connect;

    /**
     * Private constructor of CompanyDAO.
     */
    private CompanyDAO() {
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

    public void delete(Long id) {
        if (id != null) {
            try {
                this.connect.setAutoCommit(false);
                PreparedStatement computerStatement = connect.prepareStatement(SQL_DELETE_COMPUTER_WITH_COMPANY_ID);
                computerStatement.setLong(1, id);
                computerStatement.execute();
                PreparedStatement companyStatement = connect.prepareStatement(SQL_DELETE_COMPANY_WITH_ID);
                companyStatement.setLong(1, id);
                companyStatement.execute();
                this.connect.commit();
                computerStatement.close();
                companyStatement.close();
            } catch (SQLException e) {
                try {
                    logger.error("Transaction is being rolled back", e);
                    this.connect.rollback();
                } catch (SQLException e1) {
                    logger.error("error during roll back", e1);
                }
            } finally {
                try {
                    this.connect.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.error("error when setting auto commit", e);
                }
            }
        } else {
            logger.error("the computer id to delete is null");
        }


    }
}
