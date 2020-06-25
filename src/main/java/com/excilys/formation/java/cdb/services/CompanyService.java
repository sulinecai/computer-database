package com.excilys.formation.java.cdb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

@Service
public class CompanyService {

    @Autowired
    private CompanyDAO companyDAO;

    /**
     * Private constructor.
     */
    private CompanyService() {
    }

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
