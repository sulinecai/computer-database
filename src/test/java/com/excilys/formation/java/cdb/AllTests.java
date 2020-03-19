package com.excilys.formation.java.cdb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.excilys.formation.java.cdb.mappers.CompanyMapperTest;
import com.excilys.formation.java.cdb.mappers.ComputerMapperTest;
import com.excilys.formation.java.cdb.persistence.MysqlConnectTest;

@RunWith(Suite.class)
@SuiteClasses({
    CompanyMapperTest.class,
    ComputerMapperTest.class,
    MysqlConnectTest.class })
public class AllTests {

}
