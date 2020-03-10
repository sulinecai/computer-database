package com.excilys.formation.java.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.model.Computer;
import com.excilys.formation.java.cdb.persistence.MysqlConnect;

public class ComputerDAO {
	
	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";

	private static final String SQL_SELECT_ALL = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String SQL_SELECT_WITH_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	
	private Connection connect = MysqlConnect.getInstance();

	public List<Computer> listAll() throws SQLException {
		List<Computer> computerList = new ArrayList<Computer>();

		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL)){
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Computer computer = convert(resultSet);
				computerList.add(computer);
			}
			return computerList;
		} 
	}

	public Optional<Computer> findById(int id) throws SQLException {
		Connection connect = MysqlConnect.getInstance();

		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_WITH_ID)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			Computer computer = convert(resultSet);
			return Optional.of(computer);
		}
	}

//	public Optional<Computer> create(Computer computer) {
//		return Optional.empty();
//	}
//
//	public Optional<Computer> update(Computer computer) {
//		return Optional.empty();
//	}
//
//	public void delete(Computer computer) {
//	}
	
	private Computer convert(ResultSet resultSet) throws SQLException {
		Computer computer = new Computer();
		computer.setIdComputer(resultSet.getInt(ATTRIBUT_ID_COMPUTER));
		computer.setName(resultSet.getString(ATTRIBUT_NAME));
		if (resultSet.getTimestamp(ATTRIBUT_INTRODUCED) != null) { 
			computer.setIntroducedDate(resultSet.getTimestamp(ATTRIBUT_INTRODUCED).toLocalDateTime().toLocalDate());
		}
		if (resultSet.getTimestamp(ATTRIBUT_DISCONTINUED) != null) {
			computer.setDiscontinuedDate(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED).toLocalDateTime().toLocalDate());
		}
		computer.setIdCompany(resultSet.getInt(ATTRIBUT_COMPANY_ID));
		return computer;
	}

}
