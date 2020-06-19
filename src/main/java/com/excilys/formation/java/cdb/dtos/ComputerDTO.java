package com.excilys.formation.java.cdb.dtos;

public class ComputerDTO {
    private String idComputer;
    private String name;
    private String introducedDate;
    private String discontinuedDate;
    private CompanyDTO companyDTO;

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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyDTO == null) ? 0 : companyDTO.hashCode());
        result = prime * result + ((discontinuedDate == null) ? 0 : discontinuedDate.hashCode());
        result = prime * result + ((idComputer == null) ? 0 : idComputer.hashCode());
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
        ComputerDTO other = (ComputerDTO) obj;
        if (companyDTO == null) {
            if (other.companyDTO != null) {
                return false;
            }
        } else if (!companyDTO.equals(other.companyDTO)) {
            return false;
        }
        if (discontinuedDate == null) {
            if (other.discontinuedDate != null) {
                return false;
            }
        } else if (!discontinuedDate.equals(other.discontinuedDate)) {
            return false;
        }
        if (idComputer == null) {
            if (other.idComputer != null) {
                return false;
            }
        } else if (!idComputer.equals(other.idComputer)) {
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

    public static class Builder {

        private String idComputer;
        private String name;
        private String introducedDate;
        private String discontinuedDate;
        private CompanyDTO companyDTO;

        public Builder setIdComputer(String idComputer) {
            this.idComputer = idComputer;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setIntroducedDate(String introducedDate) {
            this.introducedDate = introducedDate;
            return this;
        }

        public Builder setDiscontinuedDate(String discontinuedDate) {
            this.discontinuedDate = discontinuedDate;
            return this;
        }

        public Builder setCompanyDTO(CompanyDTO companyDTO) {
            this.companyDTO = companyDTO;
            return this;
        }

        public ComputerDTO build() {
            ComputerDTO computerDTO = new ComputerDTO();
            computerDTO.idComputer = this.idComputer;
            computerDTO.name = this.name;
            computerDTO.introducedDate = this.introducedDate;
            computerDTO.discontinuedDate = this.discontinuedDate;
            computerDTO.companyDTO = this.companyDTO;
            return computerDTO;
        }
    }

}
