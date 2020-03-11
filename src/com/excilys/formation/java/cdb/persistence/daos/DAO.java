package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.persistence.MysqlConnect;

public abstract class DAO<T> {
	
	public Connection connect = MysqlConnect.getInstance();
	
	public abstract List<T> getAll();
	
	public abstract Optional<T> findById(int id);
	
	protected abstract T convert(ResultSet resultSet);

}
