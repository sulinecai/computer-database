package com.excilys.formation.java.cdb.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;

public class ComputerDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ComputerDTO computerDTO = (ComputerDTO) target;
        System.out.println("test");
        if (computerDTO.getComputerName() == null || computerDTO.getComputerName().isEmpty()) {
            errors.rejectValue("name", "null or empty");
        }
        if (computerDTO.getComputerName().equals("test")) {
            errors.rejectValue("name", "test");
        }
    }
}
