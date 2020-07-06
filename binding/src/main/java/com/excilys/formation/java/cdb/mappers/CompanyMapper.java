package com.excilys.formation.java.cdb.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.models.Company;

public class CompanyMapper {

    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Convert a Company to a CompanyDTO.
     * @param company
     * @return companyDTO
     */
    public static CompanyDTO toCompanyDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        try {
            dto.setIdCompany(company.getIdCompany().toString());
            dto.setCompanyName(company.getName());
        } catch (NullPointerException e) {
            logger.error("idCompany or name null when converting a company to a companyDTO", e);
        }
        return dto;
    }

    /**
     * Convert a CompanyDTO to a Company.
     * @param dto
     * @return company
     */
    public static Company toCompany(CompanyDTO dto) {
        Company company = new Company();
        try {
            company.setIdCompany(Long.valueOf(dto.getIdCompany()));
            company.setName(dto.getCompanyName());
        } catch (NullPointerException e) {
            logger.error("id company or name null when converting a companyDTO to a company", e);
        }
        return company;
    }

}
