package com.excilys.formation.java.cdb.restcontrollers;

import java.util.List;
import java.util.Optional;
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
import org.springframework.web.server.ResponseStatusException;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.dtos.PageDTO;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.mappers.PageMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.ComputerService;

import exceptions.NotFoundInDatabaseException;

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
    
    @GetMapping("/search/{search}")
    public List<ComputerDTO> searchComputer(@PathVariable String search, @RequestBody PageDTO pageDTO) {
        List<Computer> allComputers = computerService.findByNameByPage(search, PageMapper.toPage(pageDTO));
        return allComputers.stream().map(c -> ComputerMapper.toComputerDTO(c)).collect(Collectors.toList());
    }
    
    @GetMapping("/orderBy/{orderBy}")
    public List<ComputerDTO> orderComputer(@PathVariable String orderBy, @RequestBody PageDTO pageDTO) {
        List<Computer> allComputers = computerService.orderBy(PageMapper.toPage(pageDTO), orderBy);
        return allComputers.stream().map(c -> ComputerMapper.toComputerDTO(c)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ComputerDTO getComputer(@PathVariable Long id) {
        Optional<Computer> computerOpt = computerService.findByIdOpt(id);
        if (!computerOpt.isPresent()) {
            throw new ResponseStatusException (HttpStatus.NOT_FOUND, "The Computer is not found is the database");
        }
        return ComputerMapper.toComputerDTO(computerOpt.get());
    }
        
    @DeleteMapping("/{id}")
    public void deleteComputer(@PathVariable Long id) {
        try {
            computerService.delete(id);
        } catch (NotFoundInDatabaseException e) {
            throw new ResponseStatusException (HttpStatus.NOT_FOUND, "The Computer is not found is the database");
        }
    }
    
    @PostMapping(value = {"", "/"})
    public void createComputer(@RequestBody ComputerDTO dto) {
      computerService.create(ComputerMapper.toComputer(dto));
    }
    
    @PutMapping(value = {"", "/"})
    public void updateComputer(@RequestBody ComputerDTO dto) {
      try {
          computerService.update(ComputerMapper.toComputer(dto));
      } catch (NotFoundInDatabaseException e) {
          throw new ResponseStatusException (HttpStatus.NOT_FOUND, "The Computer is not found is the database");
      }
    }
    
}
