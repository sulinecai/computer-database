package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.Company;

public class CompanyDAO extends DAO<Company>{
	
	private static final String ATTRIBUT_ID_COMPANY = "id";
	private static final String ATTRIBUT_NAME = "name";
	
	private static final String SQL_SELECT_ALL = "SELECT id, name FROM company";
	
	private static final String SQL_SELECT_WITH_ID = "SELECT id, name FROM company WHERE id = ?";
	
	@Override
	public List<Company> getAll() {
		List<Company> companyList = new ArrayList<Company>();

		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL)){
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Company company = convert(resultSet);
				companyList.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	@Override
	public Optional<Company> findById(int id) {
		Optional<Company> result = Optional.empty();
		
		try(PreparedStatement statement = this.connect.prepareStatement(SQL_SELECT_WITH_ID)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();	
			
			while(resultSet.next()) {
				result = Optional.ofNullable(convert(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected Company convert(ResultSet resultSet) {
		Company company = new Company();
		try {
			company = new Company(resultSet.getInt(ATTRIBUT_ID_COMPANY),resultSet.getString(ATTRIBUT_NAME));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
		
}
