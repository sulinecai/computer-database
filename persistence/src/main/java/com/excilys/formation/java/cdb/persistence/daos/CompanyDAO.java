package com.excilys.formation.java.cdb.persistence.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;

import exceptions.NotFoundInDatabaseException;

@Repository
public class CompanyDAO {

    private static final String SQL_SELECT_ALL = "FROM Company";

    private static final String SQL_SELECT_WITH_ID = "FROM Company company WHERE company.id = :id";

    private static final String SQL_DELETE_COMPANY_WITH_ID = "DELETE Company WHERE id = :id";

    private static final String SQL_DELETE_COMPUTER_WITH_COMPANY_ID = "DELETE Computer WHERE company.id = :id";

    private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private SessionFactory sessionFactory;

    @Autowired
    public CompanyDAO (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Company> getAll() {
        List<Company> companyList = new ArrayList<Company>();
        try (Session session = sessionFactory.openSession()) {
            companyList = session.createQuery(SQL_SELECT_ALL, Company.class).list();
        } catch (HibernateException e) {
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
            try (Session session = sessionFactory.openSession()) {
                Query<Company>  query = session.createQuery(SQL_SELECT_ALL, Company.class);
                query.setFirstResult(page.getPageFirstLine());
                query.setMaxResults(page.getMaxLine());
                companyList = query.list();
            } catch (HibernateException e) {
                logger.error("error when listing all companies by page", e);
            }
        }
        return companyList;
    }

    public Optional<Company> findById(Long id) {
        Optional<Company> result = Optional.empty();
        if (id != null) {
            try (Session session = sessionFactory.openSession()) {
                Query<Company> query = session.createQuery(SQL_SELECT_WITH_ID, Company.class);
                query.setParameter("id", id);
                result = Optional.ofNullable(query.uniqueResult());
            } catch (HibernateException e) {
                logger.error("error when finding computer by id", e);
            }
        }
        return result;
    }

    public void delete(Long id) {
        int nbRows = 0;
        if (id != null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                nbRows += session.createQuery(SQL_DELETE_COMPUTER_WITH_COMPANY_ID).setParameter("id", id).executeUpdate();
                nbRows += session.createQuery(SQL_DELETE_COMPANY_WITH_ID).setParameter("id", id).executeUpdate();
                transaction.commit();
                if (nbRows == 0) {
                    throw new NotFoundInDatabaseException("Company not found");
                }
            } catch (IllegalStateException | PersistenceException  e) {
                logger.error("error when deleting computer", e);
            }
        } else {
            logger.error("the computer id to delete is null");
        }
    }
}
