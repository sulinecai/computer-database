package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

import exceptions.NotFoundInDatabaseException;

@Service
public class CompanyService {


    private CompanyDAO companyDAO;

    @Autowired
    public CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<Company> getAll() {
        return companyDAO.getAll();
    }

    public List<Company> getAllByPage(Page page) {
        return companyDAO.getAllByPage(page);
    }

    public Company findById(Long id) {
        Optional<Company> companyOpt = companyDAO.findById(id);
        if (!companyOpt.isPresent()) {
            throw new NotFoundInDatabaseException("The company is not found.");
        }
        return companyOpt.get();
    }

    public boolean exist(Long id) {
        return companyDAO.findById(id).isPresent();
    }

    public void delete(Long id) {
        companyDAO.delete(id);
    }
}
