package com.excilys.formation.java.cdb.dtos;

import java.time.LocalDate;

import com.excilys.formation.java.cdb.models.Company;

public class ComputerDTO {
    private String idComputer;
    private String name;
    private String introducedDate;
    private String discontinuedDate;
    private CompanyDTO companyDTO;

    public ComputerDTO() {
    }

    public ComputerDTO(String name) {
        this.name = name;
    }

    public ComputerDTO(String name, String introducedDate, String discontinuedDate, CompanyDTO companyDTO) {
        super();
        this.name = name;
        this.introducedDate = introducedDate;
        this.discontinuedDate = discontinuedDate;
        this.companyDTO = companyDTO;
    }

    public ComputerDTO(String idComputer, String name, String introducedDate, String discontinuedDate,
            CompanyDTO companyDTO) {
        super();
        this.idComputer = idComputer;
        this.name = name;
        this.introducedDate = introducedDate;
        this.discontinuedDate = discontinuedDate;
        this.companyDTO = companyDTO;
    }

    public String getIdComputer() {
        return idComputer;
    }

    public void setIdComputer(String idComputer) {
        this.idComputer = idComputer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroducedDate() {
        return introducedDate;
    }

    public void setIntroducedDate(String introducedDate) {
        this.introducedDate = introducedDate;
    }

    public String getDiscontinuedDate() {
        return discontinuedDate;
    }

    public void setDiscontinuedDate(String discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public CompanyDTO getCompany() {
        return companyDTO;
    }

    public void setCompany(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }
}
