package com.excilys.formation.java.cdb.models;

import java.time.LocalDate;

public class Computer {

    private Long idComputer;
    private String name;
    private LocalDate introducedDate;
    private LocalDate discontinuedDate;
    private Company company;

    /**
     * Default constructor.
     */
    public Computer() {
    }

    /**
     * Create a Computer with name.
     *
     * @param name
     */
    public Computer(String name) {
        this.name = name;
    }

    /**
     * Create a Computer with name.
     *
     * @param name
     * @param introducedDate
     * @param discontinuedDate
     * @param company
     */
    public Computer(String name, LocalDate introducedDate, LocalDate discontinuedDate, Company company) {
        super();
        this.name = name;
        this.introducedDate = introducedDate;
        this.discontinuedDate = discontinuedDate;
        this.company = company;
    }

    public Computer(Long idComputer, String name, LocalDate introducedDate, LocalDate discontinuedDate,
            Company company) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinuedDate == null) ? 0 : discontinuedDate.hashCode());
        result = prime * result + ((introducedDate == null) ? 0 : introducedDate.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinuedDate == null) {
            if (other.discontinuedDate != null) {
                return false;
            }
        } else if (!discontinuedDate.equals(other.discontinuedDate)) {
            return false;
        }
        if (introducedDate == null) {
            if (other.introducedDate != null) {
                return false;
            }
        } else if (!introducedDate.equals(other.introducedDate)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
