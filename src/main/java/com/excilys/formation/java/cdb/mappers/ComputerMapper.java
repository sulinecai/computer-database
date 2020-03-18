package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

public class ComputerMapper {
	
	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";
	private static final String ATTRIBUT_COMPANY_NAME = "company_name";
	
	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	
	public static Computer convert(ResultSet resultSet) {
		Computer computer = new Computer();
		try {
			computer.setIdComputer(resultSet.getLong(ATTRIBUT_ID_COMPUTER));
			computer.setName(resultSet.getString(ATTRIBUT_NAME));
			if (resultSet.getTimestamp(ATTRIBUT_INTRODUCED) != null) { 
				computer.setIntroducedDate(resultSet.getTimestamp(ATTRIBUT_INTRODUCED).toLocalDateTime().toLocalDate());
			}
			if (resultSet.getTimestamp(ATTRIBUT_DISCONTINUED) != null) {
				computer.setDiscontinuedDate(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED).toLocalDateTime().toLocalDate());
			}
			computer.setCompany(new Company(resultSet.getLong(ATTRIBUT_COMPANY_ID), resultSet.getString(ATTRIBUT_COMPANY_NAME)));
		} catch (SQLException e) {
			logger.error("sql error when converting resultset to computer");
		}
		return computer;
	}

}
