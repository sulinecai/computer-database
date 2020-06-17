package com.excilys.formation.java.cdb.models;

public class Company {

    private Long idCompany;
    private String name;

    /**
     * Default constructor.
     */
    public Company() {
    }

    /**
     * Create a company with name and id.
     *
     * @param idCompany
     * @param name
     */
    public Company(Long idCompany, String name) {
        this.idCompany = idCompany;
        this.name = name;
    }

    /**
     * Create a company with name.
     *
     * @param name
     */
    public Company(String name) {
        this.name = name;
    }

    /**
     * Create a company with id.
     *
     * @param id
     */
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idCompany == null) ? 0 : idCompany.hashCode());
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
        Company other = (Company) obj;
        if (idCompany == null) {
            if (other.idCompany != null) {
                return false;
            }
        } else if (!idCompany.equals(other.idCompany)) {
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
