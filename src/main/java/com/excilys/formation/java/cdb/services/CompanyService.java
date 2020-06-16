package com.excilys.formation.java.cdb.services;

import java.util.List;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

public class CompanyService {

    private static CompanyService companyService;

    private CompanyService() {
    }

    public static synchronized CompanyService getInstance() {
        if (companyService == null) {
            companyService = new CompanyService();
        }
        return companyService;
    }

    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    public List<Company> getAll() {
        return companyDAO.getAll();
    }

    public List<Company> getAllByPage(Page page) {
        return companyDAO.getAllByPage(page);
    }

    public Company findById(Long id) {
        return companyDAO.findById(id).get();
    }

    public boolean exist(Long id) {
        return companyDAO.findById(id).isPresent();
    }

    public void delete(Long id) {
        companyDAO.delete(id);
    }

}
