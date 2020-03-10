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

public class ComputerDAO {
	
	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";
	private static final String ATTRIBUT_COMPANY_NAME = "company_name";

	private static final String SQL_SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, "
			+ "company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id";
	
	private static final String SQL_SELECT_WITH_ID = "SELECT computer.id, computer.name, introduced, discontinued, "
			+ "company_id, company.name AS company_name FROM computer LEFT JOIN company ON "
			+ "company_id = company.id WHERE computer.id = ? ";
	
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) "
			+ "VALUES (?, ?, ?, ?)";
	
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?";
	
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";

	public List<Computer> getAll() {
		Connection connect = MysqlConnect.getInstance();
		List<Computer> computerList = new ArrayList<Computer>();

		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL)){
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Computer computer = convert(resultSet);
				computerList.add(computer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computerList;
	}

	public Optional<Computer> findById(int id) {
		Connection connect = MysqlConnect.getInstance();
		Optional<Computer> result = Optional.empty();
		
		try(PreparedStatement statement = connect.prepareStatement(SQL_SELECT_WITH_ID)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();	
			
			while(resultSet.next()) {
				result = Optional.of(convert(resultSet));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void create(Computer computer) {
		Connection connect = MysqlConnect.getInstance();

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
		Connection connect = MysqlConnect.getInstance();

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

	public int delete(Computer computer) {
		Connection connect = MysqlConnect.getInstance();

		try(PreparedStatement statement = connect.prepareStatement(SQL_DELETE)){
			statement.setInt(1, computer.getIdComputer());
			
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
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
		computer.setCompany(new Company());
		computer.getCompany().setIdCompany(resultSet.getInt(ATTRIBUT_COMPANY_ID));
		computer.getCompany().setName(resultSet.getString(ATTRIBUT_COMPANY_NAME));
		return computer;
	}

}
