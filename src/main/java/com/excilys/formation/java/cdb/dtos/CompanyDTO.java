package com.excilys.formation.java.cdb.dtos;

public class CompanyDTO {

    private String idCompany;
    private String name;

    public CompanyDTO(String idCompany, String name) {
        super();
        this.idCompany = idCompany;
        this.name = name;
    }

    public CompanyDTO(String id) {
        this.idCompany = id;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
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
        return "CompanyDTO " + idCompany + ", name: " + name;
    }

}
