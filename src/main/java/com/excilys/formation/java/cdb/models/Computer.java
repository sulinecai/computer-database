package com.excilys.formation.java.cdb.models;

import java.time.LocalDate;

public class Computer {
	
	private Long idComputer;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private Company company;
	
	public Computer() {
	}
	
	public Computer(String name) {
		this.name = name;
	}
	
	public Computer(String name, LocalDate introducedDate, LocalDate discontinuedDate, Company company) {
		super();
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.company = company;
	}
	
	public Computer(Long idComputer, String name, LocalDate introducedDate, LocalDate discontinuedDate, Company company) {
		super();
		this.idComputer = idComputer;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.company = company;
	}
	
	public Long getIdComputer() {
		return idComputer;
	}
	public void setIdComputer(Long idComputer) {
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		String companyString = "";
		if (company != null) {
			if (company.getIdCompany() != null && company.getIdCompany() != 0) {
				companyString = ", companyId:" + company.getIdCompany() + ", companyName:" + company.getName();
			}
		}
		return "Computer " + idComputer + ", name: " + name + ", introducedDate:" + introducedDate
				+ ", discontinuedDate:" + discontinuedDate + companyString;
	}
	
}
