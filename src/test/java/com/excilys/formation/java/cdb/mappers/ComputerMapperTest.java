package com.excilys.formation.java.cdb.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

public class ComputerMapperTest {
	
	private static final String ATTRIBUT_ID_COMPUTER = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";
	private static final String ATTRIBUT_COMPANY_NAME = "company_name";

	private final Long idComputer = 10l;
	private final String computerName = "computer name";
	private final Timestamp introduced = new Timestamp(200l);
	private final Timestamp discontinued = new Timestamp(300l);
	private final Long idCompany = 15l;
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
		Computer expComputer = new Computer(idComputer, computerName, introduced.toLocalDateTime().toLocalDate(), discontinued.toLocalDateTime().toLocalDate(), new Company(idCompany, companyName));
		
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
		Computer expComputer = new Computer(idComputer, computerName, null, discontinued.toLocalDateTime().toLocalDate(), new Company(idCompany, companyName));
		
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
		Computer expComputer = new Computer(idComputer, computerName, introduced.toLocalDateTime().toLocalDate(), null, new Company(idCompany, companyName));
		
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
		Computer expComputer = new Computer(idComputer, computerName, introduced.toLocalDateTime().toLocalDate(), discontinued.toLocalDateTime().toLocalDate(), new Company());
		
		assertEquals(expComputer.toString(), computer.toString());
	}

}
