package com.excilys.formation.java.cdb.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.java.cdb.models.Company;

public class CompanyRowMapper implements RowMapper<Company> {

    static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

    private static Logger logger = LoggerFactory.getLogger(CompanyRowMapper.class);

    @Override
    public Company mapRow(ResultSet resultSet, int rowNum) {
        Company company = new Company();
        try {
            company = new Company.Builder()
                    .setIdCompany(resultSet.getLong(ATTRIBUT_ID_COMPANY))
                    .setName(resultSet.getString(ATTRIBUT_NAME))
                    .build();
        } catch (SQLException e) {
            logger.error("sql error when converting resultset to company", e);
        }
        return company;
    }
}
