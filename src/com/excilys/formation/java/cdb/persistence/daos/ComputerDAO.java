package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.MysqlConnect;

public class ComputerDAO extends DAO<Computer> {
	
	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";
	private static final String ATTRIBUT_COMPANY_NAME = "company_name";

	private static final String SQL_SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, "
			+ "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.id";
	
	private static final String SQL_SELECT_WITH_ID = "SELECT computer.id, computer.name, introduced, discontinued, "
			+ "company_id, company.name AS company_name FROM computer LEFT JOIN company ON "
			+ "company_id = company.id WHERE computer.id = ? ";
	
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) "
			+ "VALUES (?, ?, ?, ?)";
	
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?";
	
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
	
	
    private static ComputerDAO computerDAO;
    
	private Connection connect = MysqlConnect.getInstance();
        
    private ComputerDAO() {}
    
    public static synchronized ComputerDAO getInstance() {
        if ( computerDAO == null ) {
        	computerDAO = new ComputerDAO();
        }
        return computerDAO;
    }

	@Override
	public List<Computer> getAll() {
		List<Computer> computerList = new ArrayList<Computer>();

		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL)){
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Computer computer = convert(resultSet);
				computerList.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}

	@Override
	public Optional<Computer> findById(int id) {
		Optional<Computer> result = Optional.empty();
		
		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_WITH_ID)){
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

	public void create(Computer computer) {
		try(PreparedStatement statement = connect.prepareStatement(SQL_INSERT)){
			statement.setString(1, computer.getName());
			Timestamp introDate = computer.getIntroducedDate() == null ? null : Timestamp.valueOf(computer.getIntroducedDate().atStartOfDay());
			statement.setTimestamp(2, introDate);
			Timestamp discontDate = computer.getDiscontinuedDate() == null ? null : Timestamp.valueOf(computer.getDiscontinuedDate().atStartOfDay());
			statement.setTimestamp(3, discontDate);
			if (computer.getCompany() == null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().getIdCompany());
			}
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Computer computer) {
		try(PreparedStatement statement = connect.prepareStatement(SQL_UPDATE)){
			statement.setString(1, computer.getName());
			Timestamp introDate = computer.getIntroducedDate() == null ? null : Timestamp.valueOf(computer.getIntroducedDate().atStartOfDay());
			statement.setTimestamp(2, introDate);
			Timestamp discontDate = computer.getDiscontinuedDate() == null ? null : Timestamp.valueOf(computer.getDiscontinuedDate().atStartOfDay());
			statement.setTimestamp(3, discontDate);
			if (computer.getCompany() == null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setInt(4, computer.getCompany().getIdCompany());
			}
			statement.setInt(5, computer.getIdComputer());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(int id) {
		try(PreparedStatement statement = connect.prepareStatement(SQL_DELETE)){
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Computer convert(ResultSet resultSet) {
		Computer computer = new Computer();
		try {
			computer.setIdComputer(resultSet.getInt(ATTRIBUT_ID_COMPUTER));
			computer.setName(resultSet.getString(ATTRIBUT_NAME));
			if (resultSet.getTimestamp(ATTRIBUT_INTRODUCED) != null) { 
				computer.setIntroducedDate(resultSet.getTimestamp(ATTRIBUT_INTRODUCED).toLocalDateTime().toLocalDate());
			}
			if (resultSet.getTimestamp(ATTRIBUT_DISCONTINUED) != null) {
				computer.setDiscontinuedDate(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED).toLocalDateTime().toLocalDate());
			}
			computer.setCompany(new Company());
			computer.getCompany().setIdCompany(resultSet.getInt(ATTRIBUT_COMPANY_ID));
			computer.getCompany().setName(resultSet.getString(ATTRIBUT_COMPANY_NAME));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

}
