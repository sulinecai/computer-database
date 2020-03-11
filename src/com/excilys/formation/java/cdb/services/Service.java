package com.excilys.formation.java.cdb.services;

import java.util.List;

public interface Service<T> {
	
	public List<T> getAll();
	
	public T findById(int id);
	
	public boolean exist(int id);

}
