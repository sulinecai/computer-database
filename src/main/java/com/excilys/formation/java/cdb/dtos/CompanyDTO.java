package com.excilys.formation.java.cdb.dtos;

public class CompanyDTO {

    private String idCompany;
    private String name;

    /**
     * Default constructor of CompanyDTO.
     */
    public CompanyDTO() {
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
        CompanyDTO other = (CompanyDTO) obj;
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

        private String idCompany;
        private String name;

        public Builder setIdCompany(String idCompany) {
            this.idCompany = idCompany;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public CompanyDTO build() {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.idCompany = this.idCompany;
            companyDTO.name = this.name;
            return companyDTO;

        }
    }

}
