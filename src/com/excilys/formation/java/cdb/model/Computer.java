package com.excilys.formation.java.cdb.model;

import java.util.Date;


public class Computer {
	
	private int idComputer;
	private String name;
	private Date introducedDate;
	private Date discontinuedDate;
	private int idCompany;
	
	public Computer() {
	}
	
	public Computer(int idComputer, String name, Date introducedDate, Date discontinuedDate, int idCompany) {
		super();
		this.idComputer = idComputer;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.idCompany = idCompany;
	}
	
	public int getIdComputer() {
		return idComputer;
	}
	public void setIdComputer(int idComputer) {
		this.idComputer = idComputer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(Date introducedDate) {
		this.introducedDate = introducedDate;
	}
	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(Date discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	public int getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	@Override
	public String toString() {
		return "Computer [idComputer=" + idComputer + ", name=" + name + ", introducedDate=" + introducedDate
				+ ", discontinuedDate=" + discontinuedDate + ", idCompany=" + idCompany + "]";
	}
	
}
