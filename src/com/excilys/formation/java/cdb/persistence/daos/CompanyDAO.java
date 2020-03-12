package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.MysqlConnect;

public class CompanyDAO extends DAO<Company>{
	
	private static final String ATTRIBUT_ID_COMPANY = "id";
	private static final String ATTRIBUT_NAME = "name";
	
	private static final String SQL_SELECT_ALL = "SELECT id, name FROM company ORDER BY id";
	
	private static final String SQL_SELECT_ALL_BY_PAGE = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";
	
	private static final String SQL_SELECT_WITH_ID = "SELECT id, name FROM company WHERE id = ?";
	
    private static CompanyDAO companyDAO;
    
	private Connection connect = MysqlConnect.getInstance();
        
    private CompanyDAO() {}
    
    public static synchronized CompanyDAO getInstance() {
        if ( companyDAO == null ) {
            companyDAO = new CompanyDAO();
        }
        return companyDAO;
    }
	
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
	
	public List<Company> getAllByPage(Page page) {
		List<Company> companyList = new ArrayList<Company>();

		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL_BY_PAGE)){
			statement.setInt(1, page.getMaxLine());
			statement.setInt(2, page.getPageFirstLine());
			
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
