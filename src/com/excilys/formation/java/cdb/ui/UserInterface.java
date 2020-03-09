package com.excilys.formation.java.cdb.ui;

import java.sql.SQLException;
import java.util.Optional;

import com.excilys.formation.java.cdb.model.Computer;
import com.excilys.formation.java.cdb.persistence.dao.ComputerDAO;

public class UserInterface {

	public static void main(String[] args) {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			Optional<Computer> cp = computerDAO.findById(5);
			System.out.println(cp.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}