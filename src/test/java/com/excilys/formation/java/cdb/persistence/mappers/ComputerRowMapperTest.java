package com.excilys.formation.java.cdb.persistence.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerRowMapperTest {

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

    @Mock
    private ResultSet resultSet;
    //private ResultSet resultSet = Mockito.mock(ResultSet.class);

    @Test
    public void testConvert() {
        try {
            when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            when(resultSet.getTimestamp(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
            when(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
            when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
            when(resultSet.getString(ATTRIBUT_COMPANY_NAME)).thenReturn(companyName);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = new ComputerRowMapper().mapRow(resultSet, 0);
        Computer expComputer = new Computer.Builder()
                .setIdComputer(idComputer)
                .setName(computerName)
                .setIntroducedDate(introduced.toLocalDateTime().toLocalDate())
                .setDiscontinuedDate(discontinued.toLocalDateTime().toLocalDate())
                .setCompany(new Company.Builder()
                        .setIdCompany(idCompany)
                        .setName(companyName)
                        .build()).build();

        assertEquals(expComputer, computer);
    }

    @Test
    public void testConvertIntroducedNull() {
        try {
            when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            when(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
            when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
            when(resultSet.getString(ATTRIBUT_COMPANY_NAME)).thenReturn(companyName);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = new ComputerRowMapper().mapRow(resultSet, 0);
        Computer expComputer = new Computer.Builder()
                .setIdComputer(idComputer)
                .setName(computerName)
                .setDiscontinuedDate(discontinued.toLocalDateTime().toLocalDate())
                .setCompany(new Company.Builder()
                        .setIdCompany(idCompany)
                        .setName(companyName)
                        .build()).build();

        assertEquals(expComputer, computer);
    }

    @Test
    public void testConvertdiscontinuedNull() {
        try {
            when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            when(resultSet.getTimestamp(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
            when(resultSet.getLong(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
            when(resultSet.getString(ATTRIBUT_COMPANY_NAME)).thenReturn(companyName);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = new ComputerRowMapper().mapRow(resultSet, 0);
        Computer expComputer = new Computer.Builder()
                .setIdComputer(idComputer)
                .setName(computerName)
                .setIntroducedDate(introduced.toLocalDateTime().toLocalDate())
                .setCompany(new Company.Builder()
                        .setIdCompany(idCompany)
                        .setName(companyName)
                        .build()).build();
        assertEquals(expComputer, computer);
    }

    @Test
    public void testConvertCompanyNull() {
        try {
            when(resultSet.getLong(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(computerName);
            when(resultSet.getTimestamp(ATTRIBUT_INTRODUCED)).thenReturn(introduced);
            when(resultSet.getTimestamp(ATTRIBUT_DISCONTINUED)).thenReturn(discontinued);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Computer computer = new ComputerRowMapper().mapRow(resultSet, 0);
        Computer expComputer = new Computer.Builder()
                .setIdComputer(idComputer)
                .setName(computerName)
                .setIntroducedDate(introduced.toLocalDateTime().toLocalDate())
                .setDiscontinuedDate(discontinued.toLocalDateTime().toLocalDate())
                .build();

        assertEquals(expComputer.toString(), computer.toString());
    }

}
