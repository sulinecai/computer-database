package com.excilys.formation.java.cdb.dtos;

public class CompanyDTO {

    private String idCompany;
    private String name;

    /**
     * Default constructor of CompanyDTO.
     */
    public CompanyDTO() {
    }

    /**
     * Constructor with idCompany and name.
     * @param idCompany
     * @param name
     */
    public CompanyDTO(String idCompany, String name) {
        this.idCompany = idCompany;
        this.name = name;
    }

    /**
     * Constructor with idCompany and name.
     * @param idCompany
     */
    public CompanyDTO(String idCompany) {
        this.idCompany = idCompany;
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

    @Override
    public int hashCode() {
        return Integer.valueOf(this.idCompany);
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof CompanyDTO) {
            CompanyDTO dto = (CompanyDTO) o;
            result = (this.idCompany.equals(dto.getIdCompany()) && this.getName().equals(dto.getName()));
        }
        return result;
    }
}
