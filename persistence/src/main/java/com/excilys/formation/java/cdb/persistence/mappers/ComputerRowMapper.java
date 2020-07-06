package com.excilys.formation.java.cdb.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

    private static final String ATTRIBUT_ID_COMPUTER = "id";
    private static final String ATTRIBUT_NAME = "name";
    private static final String ATTRIBUT_INTRODUCED = "introduced";
    private static final String ATTRIBUT_DISCONTINUED = "discontinued";
    private static final String ATTRIBUT_COMPANY_ID = "company_id";
    private static final String ATTRIBUT_COMPANY_NAME = "company_name";

    private static Logger logger = LoggerFactory.getLogger(ComputerRowMapper.class);

    @Override
    public Computer mapRow(ResultSet resultSet, int rowNum) {
        Computer computer = new Computer();
        try {
            computer.setIdComputer(resultSet.getLong(ATTRIBUT_ID_COMPUTER));
            computer.setName(resultSet.getString(ATTRIBUT_NAME));
            if (resultSet.getTimestamp(ATTRIBUT_INTRODUCED) != null) {
                computer.setIntroducedDate(resultSet.getTimestamp(ATTRIBUT_INTRODUCED).toLocalDateTime().toLocalDate());
            }
            if (resultSet.getTimestamp(ATTRIBUT_DISCONTINUED) != null) {
                computer.setDiscontinuedDate(
                        resultSet.getTimestamp(ATTRIBUT_DISCONTINUED).toLocalDateTime().toLocalDate());
            }
            computer.setCompany(new Company.Builder()
                    .setIdCompany(resultSet.getLong(ATTRIBUT_COMPANY_ID))
                    .setName(resultSet.getString(ATTRIBUT_COMPANY_NAME))
                    .build());
        } catch (SQLException e) {
            logger.error("sql error when converting resultset to computer");
        }
        return computer;
    }

    public static Timestamp localDateToTimestamp(LocalDate localDate) {
        Timestamp result = null;
        if (localDate != null) {
            if (localDate.isEqual(LocalDate.of(1970, 1, 1))) {
                result = Timestamp.valueOf(localDate.atStartOfDay().plusHours(1).plusSeconds(1));
            } else {
                result = Timestamp.valueOf(localDate.atStartOfDay());
            }
        }
        return result;
    }
}
