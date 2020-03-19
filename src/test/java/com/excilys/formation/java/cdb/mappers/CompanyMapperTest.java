package com.excilys.formation.java.cdb.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.formation.java.cdb.models.Company;

public class CompanyMapperTest {

    private static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

    private final Long id = 10L;
    private final String name = "test name";

    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    @Test
    public void testConvert() {
        try {
            Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPANY)).thenReturn(id);
            Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(name);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Company company = CompanyMapper.convert(resultSet);
        Company expCompany = new Company(id, name);

        assertEquals(expCompany.getIdCompany(), company.getIdCompany());
        assertEquals(expCompany.getName(), company.getName());
    }

    @Test
    public void testConvertIdNull() {
        try {
            Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(name);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Company company = CompanyMapper.convert(resultSet);
        Company expCompany = new Company(0L, name);

        assertEquals(expCompany.getIdCompany(), company.getIdCompany());
        assertEquals(expCompany.getName(), company.getName());
    }

    @Test
    public void testConvertNameNull() {
        try {
            Mockito.when(resultSet.getLong(ATTRIBUT_ID_COMPANY)).thenReturn(id);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Company company = CompanyMapper.convert(resultSet);
        Company expCompany = new Company(id, null);

        assertEquals(expCompany.getIdCompany(), company.getIdCompany());
        assertEquals(expCompany.getName(), company.getName());
    }

}
