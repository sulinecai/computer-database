package com.excilys.formation.java.cdb.persistence.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.mappers.ComputerRowMapper;

@Repository
public class ComputerDAO {

//    private static final String SQL_SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, "
//            + "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id";
    private static final String SQL_SELECT_ALL = "FROM Computer";
    //from Computer as computer left join computer.company.id as kittens
    //private static final String SQL_COUNT_ALL = "SELECT COUNT(id) AS total FROM computer";
    private static final String SQL_COUNT_ALL = "select count(computer) from Computer computer";

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
    JdbcTemplate jdbcTemplate;

    private SessionFactory sessionFactory;

    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);


    @Autowired
    public ComputerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int getNumberComputers() {
        int count = -1;
        try (Session session = sessionFactory.openSession()) {
            count = session.createQuery(SQL_COUNT_ALL, Long.class).uniqueResult().intValue();
        } catch (HibernateException e) {
            logger.error("error when getting total number of computers: ", e);
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
            try (Session session = sessionFactory.openSession()) {
                Query<Computer> query = session.createQuery(SQL_SELECT_ALL, Computer.class);
                query.setFirstResult(page.getPageFirstLine());
                query.setMaxResults(page.getMaxLine());
                computerList = query.list();

                //computerList = jdbcTemplate.query(SQL_SELECT_ALL.concat(SQL_OFFSET), new ComputerRowMapper(), page.getMaxLine(), page.getPageFirstLine());
            } catch (HibernateException e) {
                logger.error("error when get all by page:", e);
            }
        }
        return computerList;
    }

    public Optional<Computer> findById(Long id) {
        Optional<Computer> result = Optional.empty();
        if (id != null) {
            try {
                result = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_WITH_ID, new ComputerRowMapper(), id));
            } catch (EmptyResultDataAccessException e) {
                logger.info("computer with id %d not found", id);
            } catch (DataAccessException e) {
                logger.error("error when get finding computer by id", e);
            }
        } else {
            logger.error("company id to find is null");
        }
        return result;
    }

    public List<Computer> findByNameByPage(String name, Page page) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (page.getCurrentPage() > 0 && name != null && !name.isEmpty() && !name.contains("%") && !name.contains("_")) {
            try {
                computerList = jdbcTemplate.query(SQL_SELECT_WITH_NAME.concat(SQL_OFFSET), new ComputerRowMapper(),
                        "%".concat(name).concat("%"),
                        "%".concat(name).concat("%"),
                        page.getMaxLine(),
                        page.getPageFirstLine());
            } catch (DataAccessException e) {
                logger.error("error when finding computer with name", e);
            }
        }
        return computerList;
    }

    public List<Computer> findAllByName(String name) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (name != null && !name.isEmpty() && !name.contains("%") && !name.contains("_")) {
            try {
                computerList = jdbcTemplate.query(SQL_SELECT_WITH_NAME, new ComputerRowMapper(),
                        "%".concat(name).concat("%"),
                        "%".concat(name).concat("%"));
            } catch (DataAccessException e) {
                logger.error("error when finding computer with name", e);
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
        int nbRows = 0;
        if (computer != null) {
            try {
                nbRows = jdbcTemplate.update(SQL_INSERT,
                        computer.getName(),
                        ComputerRowMapper.localDateToTimestamp(computer.getIntroducedDate()),
                        ComputerRowMapper.localDateToTimestamp(computer.getDiscontinuedDate()),
                        computer.getCompany() == null ? null : computer.getCompany().getIdCompany());
                if (nbRows != 1) {
                    logger.info("%d rows affected when creating computer", nbRows);
                }
            } catch (DataAccessException e) {
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
        int nbRows = 0;
        if (computer != null) {
            try {
                nbRows = jdbcTemplate.update(SQL_UPDATE,
                        computer.getName(),
                        ComputerRowMapper.localDateToTimestamp(computer.getIntroducedDate()),
                        ComputerRowMapper.localDateToTimestamp(computer.getDiscontinuedDate()),
                        computer.getCompany() == null ? null : computer.getCompany().getIdCompany(),
                        computer.getIdComputer());
                if (nbRows != 1) {
                    logger.info("%d rows affected when updating computer", nbRows);
                }
            } catch (DataAccessException e) {
                logger.error("error when creating a computer", e);
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
        int nbRows = 0;
        if (id != null) {
            try {
                nbRows = jdbcTemplate.update(SQL_DELETE, id);
                if (nbRows != 1) {
                    logger.info("%d rows affected when deleting computer", nbRows);
                }
            } catch (DataAccessException e) {
                logger.error("error when deleting computer", e);
            }
        } else {
            logger.error("the id of the computer to delete is null");
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
            //requete = requete.concat(SQL_OFFSET);
            break;
        }


        try (Session session = sessionFactory.openSession()) {
            Query<Computer> query = session.createQuery(SQL_SELECT_ALL, Computer.class);
            query.setFirstResult(page.getPageFirstLine());
            query.setMaxResults(page.getMaxLine());
            computerList = query.list();

            //computerList = jdbcTemplate.query(SQL_SELECT_ALL.concat(SQL_OFFSET), new ComputerRowMapper(), page.getMaxLine(), page.getPageFirstLine());
        } catch (HibernateException e) {
            logger.error("error when get all by page:", e);
        }
//        try {
//            computerList = jdbcTemplate.query(requete, new ComputerRowMapper(), page.getMaxLine(), page.getPageFirstLine());
//        } catch (DataAccessException e) {
//            logger.error("error when sorting computers", e);
//        }
        return computerList;
    }

}
