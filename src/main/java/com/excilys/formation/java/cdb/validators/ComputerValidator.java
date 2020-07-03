package com.excilys.formation.java.cdb.validators;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerValidator {
    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

    public static boolean dateFormatValidator(String date) {
        boolean dateIsValid = true;
        try {
            if (!date.isEmpty()) {
                LocalDate.parse(date);
            }
        } catch (DateTimeParseException e) {
            dateIsValid = false;
            logger.error("invalid date format: " + date);
        } catch (NullPointerException e) {
            logger.info("the date is null");
        }
        return dateIsValid;
    }
}
