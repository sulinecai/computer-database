package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.models.Company;

public class CompanyMapper {

    static final String ATTRIBUT_ID_COMPANY = "id";
    private static final String ATTRIBUT_NAME = "name";

    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Convert a result set to the associated Company object.
     *
     * @param resultSet
     * @return converted Company object
     */

    public static Company convert(ResultSet resultSet) {
        Company company = new Company();
        try {
            company = new Company(resultSet.getLong(ATTRIBUT_ID_COMPANY), resultSet.getString(ATTRIBUT_NAME));
        } catch (SQLException e) {
            logger.error("sql error when converting resultset to company");
        }
        return company;
    }
}
