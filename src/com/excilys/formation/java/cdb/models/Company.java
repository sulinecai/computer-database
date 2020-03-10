package com.excilys.formation.java.cdb.models;

public class Company {
	
	private int idCompany;
	private String name;
	
	public Company() {
	}
	
	public Company(int idCompany, String name) {
		super();
		this.idCompany = idCompany;
		this.name = name;
	}
	
	public Company(String name) {
		super();
		this.name = name;
	}

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [idCompany=" + idCompany + ", name=" + name + "]";
	}
}
