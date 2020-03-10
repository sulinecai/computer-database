package com.excilys.formation.java.cdb.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;

public class ComputerService {
	
	private ComputerDAO computerDAO = new ComputerDAO();
	
	public List<Computer> getAll() throws SQLException {
		return computerDAO.getAll();
	}
	
	public Optional<Computer> findById(int id) throws SQLException {
		return computerDAO.findById(id);
	}
	
	public boolean create(Computer computer) {
		boolean allowed = true;
		if (allowedToCreate(computer)) {
			computerDAO.create(computer);
		} else {
			allowed = false;
		}
		return allowed;
	}
	
	private boolean allowedToCreate(Computer computer) {
		boolean allowed = true;
		if (computer.getName() == null) {
			allowed = false;
		} else if (computer.getDiscontinuedDate() != null) {
			if (computer.getIntroducedDate() == null) {
				allowed = false;
			} else if (computer.getDiscontinuedDate().isBefore(computer.getIntroducedDate())) {
				allowed = false;
			}
			//TODO : check that the company exists
		}
		return allowed;
	}
	
	public boolean update(Computer computer) throws SQLException {
		boolean allowed = true;
		if (findById(computer.getIdComputer()).isPresent()) {
			computerDAO.update(computer);
		} else {
			allowed = false;
		}
		return allowed;
	}
}
