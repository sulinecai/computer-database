package com.excilys.formation.java.cdb.mappers;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.models.Computer;

public class ComputerMapper {

    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    /**
     * Convert a Computer to a ComputerDTO.
     *
     * @param computer
     * @return computerDTO
     */
    public static ComputerDTO toComputerDTO(Computer computer) {
        ComputerDTO dto = new ComputerDTO();
        try {
            dto.setIdComputer(computer.getIdComputer().toString());
            dto.setComputerName(computer.getName());
            if (computer.getIntroducedDate() == null) {
                dto.setIntroducedDate("");
            } else {
                dto.setIntroducedDate(computer.getIntroducedDate().toString());
            }
            if (computer.getDiscontinuedDate() == null) {
                dto.setDiscontinuedDate("");
            } else {
                dto.setDiscontinuedDate(computer.getDiscontinuedDate().toString());
            }
            dto.setCompanyDTO(CompanyMapper.toCompanyDTO(computer.getCompany()));
        } catch (RuntimeException e) {
            logger.error("error when converting a computerDTO to a computer");
        }
        return dto;
    }

    /**
     * Convert a ComputerDTO to a Computer.
     *
     * @param dto
     * @return computer
     */
    public static Computer toComputer(ComputerDTO dto) {
        Computer computer = new Computer();
        try {
            if (dto.getIdComputer() != null) {
                computer.setIdComputer(Long.valueOf(dto.getIdComputer()));
            }
            computer.setName(dto.getComputerName());
            if (dto.getIntroducedDate() != null && !dto.getIntroducedDate().equals("")) {
                computer.setIntroducedDate(LocalDate.parse(dto.getIntroducedDate()));
            }
            if (dto.getDiscontinuedDate() != null && !dto.getDiscontinuedDate().equals("")) {
                computer.setDiscontinuedDate(LocalDate.parse(dto.getDiscontinuedDate()));
            }
            if (dto.getCompanyDTO() != null) {
                computer.setCompany(CompanyMapper.toCompany(dto.getCompanyDTO()));
            }
        } catch (RuntimeException e) {
            logger.error("error when converting a computerDTO to a computer : " + e.toString());
        }
        return computer;
    }

}
