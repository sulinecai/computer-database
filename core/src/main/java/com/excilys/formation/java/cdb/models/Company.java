package com.excilys.formation.java.cdb.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompany;
    private String name;

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

    public static class Builder {

        private Long idCompany;
        private String name;

        public Builder setIdCompany(Long idCompany) {
            this.idCompany = idCompany;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Company build() {
            Company company = new Company();
            company.idCompany = this.idCompany;
            company.name = this.name;
            return company;
        }
    }

}
