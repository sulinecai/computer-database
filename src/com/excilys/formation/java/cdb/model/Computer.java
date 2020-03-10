package com.excilys.formation.java.cdb.model;

import java.time.LocalDate;

public class Computer {
	
	private int idComputer;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private int idCompany;
	
	public Computer() {
	}
	
	public Computer(int idComputer, String name, LocalDate introducedDate, LocalDate discontinuedDate, int idCompany) {
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
	public LocalDate getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}
	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(LocalDate discontinuedDate) {
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
