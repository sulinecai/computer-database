package com.excilys.formation.java.cdb.dtos;

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

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }

    @Override
    public String toString() {
        String companyString = "";
        if (companyDTO != null) {
            if (companyDTO.getIdCompany() != null && companyDTO.getIdCompany() != "0") {
                companyString = ", companyId:" + companyDTO.getIdCompany() + ", companyName:" + companyDTO.getName();
            }
        }
        return "Computer " + idComputer + ", name: " + name + ", introducedDate:" + introducedDate
                + ", discontinuedDate:" + discontinuedDate + companyString;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(this.idComputer);
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof ComputerDTO) {
            ComputerDTO dto = (ComputerDTO) o;
            result = this.getIdComputer().equals(dto.getIdComputer());
            result = result && this.getName().equals(dto.getName());
            result = result && this.introducedDate.equals(dto.getIntroducedDate());
            result = result && this.discontinuedDate.equals(dto.getDiscontinuedDate());
            result = result && this.companyDTO.equals(dto.getCompanyDTO());
        }
        return result;
    }
}
