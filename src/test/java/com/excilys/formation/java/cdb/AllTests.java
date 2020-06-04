package com.excilys.formation.java.cdb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.excilys.formation.java.cdb.mappers.CompanyMapperTest;
import com.excilys.formation.java.cdb.mappers.ComputerMapperTest;
import com.excilys.formation.java.cdb.persistence.MysqlConnectTest;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAOTest;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAOTest;

@RunWith(Suite.class)
@SuiteClasses({
    CompanyMapperTest.class,
    ComputerMapperTest.class,
    MysqlConnectTest.class,
    CompanyDAOTest.class,
    ComputerDAOTest.class
    })
public class AllTests {

}
