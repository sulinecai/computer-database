package com.excilys.formation.java.cdb.models;

public class Company {

    private Long idCompany;
    private String name;

    public Company() {
    }

    public Company(Long idCompany, String name) {
        super();
        this.idCompany = idCompany;
        this.name = name;
    }

    public Company(String name) {
        super();
        this.name = name;
    }

    public Company(Long id) {
        this.idCompany = id;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
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
        return "Company " + idCompany + ", name: " + name;
    }
}
