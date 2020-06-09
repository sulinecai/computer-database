package com.excilys.formation.java.cdb.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

public class ComputerMapperTest {

    private static final String ATTRIBUT_ID_COMPUTER = "id";
    private static final String ATTRIBUT_NAME = "name";
    private static final String ATTRIBUT_INTRODUCED = "introduced";
    private static final String ATTRIBUT_DISCONTINUED = "discontinued";
    private static final String ATTRIBUT_COMPANY_ID = "company_id";
    private static final String ATTRIBUT_COMPANY_NAME = "company_name";

    private final Long idComputer = 10L;
    private final String computerName = "computer name";
    private final Timestamp introduced = new Timestamp(200L);
    private final Timestamp discontinued = new Timestamp(300L);
    private final Long idCompany = 15L;
    private final String companyName = "company name";

    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    @Test
    public void testConvert() {
        try {
            Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            Mockito.when(resultSet.getTimestamp(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
            Mockito.when(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
            Mockito.when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
            Mockito.when(resultSet.getString(ATTRIBUT_COMPANY_NAME)).thenReturn(companyName);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = ComputerMapper.convert(resultSet);
        Computer expComputer = new Computer(idComputer, computerName, introduced.toLocalDateTime().toLocalDate(),
                discontinued.toLocalDateTime().toLocalDate(), new Company(idCompany, companyName));

        assertEquals(expComputer.toString(), computer.toString());
    }

    @Test
    public void testConvertIntroducedNull() {
        try {
            Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            Mockito.when(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
            Mockito.when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
            Mockito.when(resultSet.getString(ATTRIBUT_COMPANY_NAME)).thenReturn(companyName);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = ComputerMapper.convert(resultSet);
        Computer expComputer = new Computer(idComputer, computerName, null,
                discontinued.toLocalDateTime().toLocalDate(), new Company(idCompany, companyName));

        assertEquals(expComputer.toString(), computer.toString());
    }

    @Test
    public void testConvertdiscontinuedNull() {
        try {
            Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            Mockito.when(resultSet.getTimestamp(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
            Mockito.when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
            Mockito.when(resultSet.getString(ATTRIBUT_COMPANY_NAME)).thenReturn(companyName);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = ComputerMapper.convert(resultSet);
        Computer expComputer = new Computer(idComputer, computerName, introduced.toLocalDateTime().toLocalDate(), null,
                new Company(idCompany, companyName));

        assertEquals(expComputer.toString(), computer.toString());
    }

    @Test
    public void testConvertCompanyNull() {
        try {
            Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            Mockito.when(resultSet.getTimestamp(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
            Mockito.when(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = ComputerMapper.convert(resultSet);
        Computer expComputer = new Computer(idComputer, computerName, introduced.toLocalDateTime().toLocalDate(),
                discontinued.toLocalDateTime().toLocalDate(), new Company());

        assertEquals(expComputer.toString(), computer.toString());
    }

    /**
     * Test that the Computer is correctly converted to a ComputerDTO.
     */
    @Test
    public void testToComputerDTO() {
        Computer computer = new Computer(1L, "id", LocalDate.of(2010, 3, 5), LocalDate.of(2016, 4, 7), new Company(2L, "company"));
        ComputerDTO dto = ComputerMapper.toComputerDTO(computer);
        assertEquals(new ComputerDTO("1", "id", "2010-03-05", "2016-04-07", new CompanyDTO("2", "company")), dto);
    }

    /**
     * Test that the ComputerDTO is correctly converted to a Computer.
     */
    @Test
    public void testToComputer() {
        ComputerDTO dto = new ComputerDTO("1", "id", "2010-03-05", "2016-04-07", new CompanyDTO("2", "company"));
        Computer computer = ComputerMapper.toComputer(dto);
        assertEquals(new Computer(1L, "id", LocalDate.of(2010, 3, 5), LocalDate.of(2016, 4, 7), new Company(2L, "company")), computer);
    }
}
