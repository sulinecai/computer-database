package com.excilys.formation.java.cdb.dtos;

public class CompanyDTO {

    private String idCompany;
    private String companyName;

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "CompanyDTO " + idCompany + ", name: " + companyName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idCompany == null) ? 0 : idCompany.hashCode());
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
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
        if (companyName == null) {
            if (other.companyName != null) {
                return false;
            }
        } else if (!companyName.equals(other.companyName)) {
            return false;
        }
        return true;
    }

    public static class Builder {

        private String idCompany;
        private String companyName;

        public Builder setIdCompany(String idCompany) {
            this.idCompany = idCompany;
            return this;
        }

        public Builder setCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public CompanyDTO build() {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.idCompany = this.idCompany;
            companyDTO.companyName = this.companyName;
            return companyDTO;

        }
    }

}
