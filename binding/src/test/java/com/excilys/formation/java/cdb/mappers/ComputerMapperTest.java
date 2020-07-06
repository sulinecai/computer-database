package com.excilys.formation.java.cdb.mappers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

public class ComputerMapperTest {



    /**
     * Test that the Computer is correctly converted to a ComputerDTO.
     */
    @Test
    public void testToComputerDTO() {
        Computer computer = new Computer.Builder()
                .setIdComputer(1L)
                .setName("id")
                .setIntroducedDate(LocalDate.of(2010, 3, 5))
                .setDiscontinuedDate(LocalDate.of(2016, 4, 7))
                .setCompany(new Company.Builder()
                        .setIdCompany(2L)
                        .setName("company")
                        .build()).build();
        ComputerDTO dto = ComputerMapper.toComputerDTO(computer);
        ComputerDTO expectedDTO = new ComputerDTO.Builder()
                .setIdComputer("1")
                .setName("id")
                .setIntroducedDate("2010-03-05")
                .setDiscontinuedDate("2016-04-07")
                .setCompanyDTO(new CompanyDTO.Builder()
                        .setIdCompany("2")
                        .setCompanyName("company").build()).build();
        assertEquals(expectedDTO, dto);
    }

    /**
     * Test that the ComputerDTO is correctly converted to a Computer.
     */
    @Test
    public void testToComputer() {
        ComputerDTO dto = new ComputerDTO.Builder()
            .setIdComputer("1")
            .setName("id")
            .setIntroducedDate("2010-03-05")
            .setDiscontinuedDate("2016-04-07")
            .setCompanyDTO(new CompanyDTO.Builder()
                    .setIdCompany("2")
                    .setCompanyName("company").build()).build();

        Computer computer = ComputerMapper.toComputer(dto);
        Computer expComputer = new Computer.Builder()
                .setIdComputer(1L)
                .setName("id")
                .setIntroducedDate(LocalDate.of(2010, 3, 5))
                .setDiscontinuedDate(LocalDate.of(2016, 4, 7))
                .setCompany(new Company.Builder()
                    .setIdCompany(2L)
                    .setName("company")
                    .build()).build();
        assertEquals(expComputer, computer);
    }
}
