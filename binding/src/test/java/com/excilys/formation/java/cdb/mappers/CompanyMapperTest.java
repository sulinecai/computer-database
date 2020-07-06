package com.excilys.formation.java.cdb.mappers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.models.Company;

public class CompanyMapperTest {

    /**
     * Test that the Company is correctly converted to a CompanyDTO.
     */
    @Test
    public void testToCompanyDTO() {
        Company company = new Company.Builder()
                .setIdCompany(1L)
                .setName("test")
                .build();
        CompanyDTO dto = CompanyMapper.toCompanyDTO(company);
        assertEquals(new CompanyDTO.Builder()
                .setIdCompany("1")
                .setCompanyName("test").build(), dto);
    }

    /**
     * Test that the CompanyDTO is correctly converted to a Company.
     */
    @Test
    public void testToCompany() {
        CompanyDTO dto = new CompanyDTO.Builder()
            .setIdCompany("1")
            .setCompanyName("test").build();
        Company company = CompanyMapper.toCompany(dto);
        assertEquals(new Company.Builder().setIdCompany(1L).setName("test").build(), company);
    }
}
