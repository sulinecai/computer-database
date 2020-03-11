package com.excilys.formation.java.cdb.services;

import java.util.List;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

public class CompanyService implements Service<Company> {

	private CompanyDAO companyDAO = new CompanyDAO();

	@Override
	public List<Company> getAll() {
		return companyDAO.getAll();
	}

	@Override
	public Company findById(int id) {
		return companyDAO.findById(id).get();
	}

	@Override
	public boolean exist(int id) {
		return companyDAO.findById(id).isPresent();
	}

}
