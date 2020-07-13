package com.excilys.formation.java.cdb.restcontrollers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.ComputerService;

@RestController
@RequestMapping("computers")
public class ComputerRESTController {
    
    @Autowired
    private ComputerService computerService;

    @GetMapping(value = {"", "/"})
    public List<ComputerDTO> listComputers() {
        List<Computer> allComputers = computerService.getAll();
        return allComputers.stream().map(c -> ComputerMapper.toComputerDTO(c)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ComputerDTO getComputer(@PathVariable Long id) {
        ComputerDTO dto = new ComputerDTO();
        if (computerService.exist(id)) {
            dto = ComputerMapper.toComputerDTO(computerService.findById(id));
        }
        return dto;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteComputer(@PathVariable Long id) {
        if (computerService.exist(id)) {
            computerService.delete(id);
            return HttpStatus.OK;
        }  
        return HttpStatus.NOT_FOUND;
    }
    
    @PostMapping(value = {"", "/"})
    public ComputerDTO createComputer(@RequestBody ComputerDTO dto) {
      computerService.create(ComputerMapper.toComputer(dto));
      return dto;
    }
    
    @PutMapping(value = {"", "/"})
    public ComputerDTO updateComputer(@RequestBody ComputerDTO dto) {
      computerService.update(ComputerMapper.toComputer(dto));
      return dto;
    }
    
}
