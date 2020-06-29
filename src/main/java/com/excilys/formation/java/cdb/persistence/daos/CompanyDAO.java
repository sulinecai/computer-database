package com.excilys.formation.java.cdb.persistence.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    JdbcTemplate jdbcTemplate;

    /**
     * Private constructor of CompanyDAO.
     */
    private CompanyDAO() {
    }

    public List<Company> getAll() {
        List<Company> companyList = new ArrayList<Company>();
        try {
            companyList = jdbcTemplate.query(SQL_SELECT_ALL, new CompanyMapper());
        } catch (DataAccessException e) {
            logger.error("error when getting all companies", e);
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
            try {
                companyList = jdbcTemplate.query(SQL_SELECT_ALL_BY_PAGE, new CompanyMapper(), page.getMaxLine(), page.getPageFirstLine());
            } catch (DataAccessException e) {
                logger.error("error when listing all companies by page", e);
            }
        }
        return companyList;
    }

    public Optional<Company> findById(Long id) {
        Optional<Company> result = Optional.empty();
        if (id != null) {
            try {
                result = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_WITH_ID, new CompanyMapper(), id));
            } catch (EmptyResultDataAccessException e) {
                logger.info("company with id %d not found", id);
            } catch (DataAccessException e) {
                logger.error("error when get finding company by id", e);
            }
        }
        return result;
    }

    @Transactional
    public void delete(Long id) {
        if (id != null) {
            try {
                jdbcTemplate.update(SQL_DELETE_COMPUTER_WITH_COMPANY_ID, id);
                jdbcTemplate.update(SQL_DELETE_COMPANY_WITH_ID, id);
            } catch (DataAccessException e) {
                logger.error("error when deleting computer", e);
            }
        } else {
            logger.error("the computer id to delete is null");
        }
    }
}
