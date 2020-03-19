package com.excilys.formation.java.cdb.services;

import java.util.List;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

public class CompanyService implements Service<Company> {

    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    @Override
    public List<Company> getAll() {
        return companyDAO.getAll();
    }

    public List<Company> getAllByPage(Page page) {
        return companyDAO.getAllByPage(page);
    }

    @Override
    public Company findById(Long id) {
        return companyDAO.findById(id).get();
    }

    @Override
    public boolean exist(Long id) {
        return companyDAO.findById(id).isPresent();
    }

}
