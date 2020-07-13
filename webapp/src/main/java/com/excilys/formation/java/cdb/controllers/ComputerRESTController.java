package com.excilys.formation.java.cdb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;

@RestController
public class ComputerRESTController {

    @GetMapping("/computer")
    public ComputerDTO listComputer() {
        List<ComputerDTO> allComputerDTOs = new ArrayList<ComputerDTO>();
        //allComputerDTOs = allComputers.stream().map(c -> ComputerMapper.toComputerDTO(c)).collect(Collectors.toList());

        return new ComputerDTO.Builder().setName("computer test").setIdComputer("10").build();
    }
}
