package com.excilys.formation.java.cdb.persistence.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.formation.java.cdb.models.Company;

@RunWith(MockitoJUnitRunner.class)
public class CompanyRowMapperTest {

    private static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

    private final Long id = 10L;
    private final String name = "test name";

    @Mock
    private ResultSet resultSet;

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
        Company company = new CompanyRowMapper().mapRow(resultSet, 0);
        Company expCompany = new Company.Builder()
                .setIdCompany(id)
                .setName(name)
                .build();

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
        Company company = new CompanyRowMapper().mapRow(resultSet, 0);
        Company expCompany = new Company.Builder()
                .setIdCompany(0L)
                .setName(name)
                .build();

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
        Company company = new CompanyRowMapper().mapRow(resultSet, 0);
        Company expCompany = new Company.Builder()
                .setIdCompany(id)
                .build();

        assertEquals(expCompany.getIdCompany(), company.getIdCompany());
        assertEquals(expCompany.getName(), company.getName());
    }

}
