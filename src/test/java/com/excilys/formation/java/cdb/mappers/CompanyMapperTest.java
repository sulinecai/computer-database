package com.excilys.formation.java.cdb.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.formation.java.cdb.models.Company;

public class CompanyMapperTest {

    private static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

    private final Long id = 10L;
    private final String name = "test name";

    private ResultSet resultSet = mock(ResultSet.class);

    /**
     * Test that the resultSet is correctly converted in Company object.
     */
    @Test
    public void testConvert() {
        try {
            when(resultSet.getLong(ATTRIBUT_ID_COMPANY)).thenReturn(id);
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(name);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Company company = CompanyMapper.convert(resultSet);
        Company expCompany = new Company(id, name);

        assertEquals(expCompany.getIdCompany(), company.getIdCompany());
        assertEquals(expCompany.getName(), company.getName());
    }

    /**
     * Test that the resultSet with id null is correctly converted in Company object.
     */
    @Test
    public void testConvertIdNull() {
        try {
            when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(name);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Company company = CompanyMapper.convert(resultSet);
        Company expCompany = new Company(0L, name);

        assertEquals(expCompany.getIdCompany(), company.getIdCompany());
        assertEquals(expCompany.getName(), company.getName());
    }

    /**
     * Test that the resultSet with name null is correctly converted in Company object.
     */
    @Test
    public void testConvertNameNull() {
        try {
            when(resultSet.getLong(ATTRIBUT_ID_COMPANY)).thenReturn(id);
        } catch (SQLException e) {
            fail("sql exception :" + e.getMessage());
        }
        Company company = CompanyMapper.convert(resultSet);
        Company expCompany = new Company(id, null);

        assertEquals(expCompany.getIdCompany(), company.getIdCompany());
        assertEquals(expCompany.getName(), company.getName());
    }

}
