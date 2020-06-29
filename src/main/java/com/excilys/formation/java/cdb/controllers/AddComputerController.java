package com.excilys.formation.java.cdb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.validators.ComputerValidator;

@Controller
@RequestMapping("/AddComputer")
public class AddComputerController {

    private static Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;

    @GetMapping
    public ModelAndView showInfo(){
        List<Company> allCompanies = companyService.getAll();
        List<CompanyDTO> allCompanyDTOs = new ArrayList<CompanyDTO>();
        for (Company c : allCompanies) {
            allCompanyDTOs.add(CompanyMapper.toCompanyDTO(c));
        }
        ModelAndView modelAndView = new ModelAndView("addComputer");
        modelAndView.addObject("companies", allCompanyDTOs);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView addComputer(
            @RequestParam String computerName,
            @RequestParam String introduced,
            @RequestParam String discontinued,
            @RequestParam String companyId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/ListComputers");
        ComputerDTO computerDTO = new ComputerDTO.Builder().setName(computerName).build();
        if (!introduced.isEmpty()) {
            computerDTO.setIntroducedDate(introduced);
        }
        if (!discontinued.isEmpty()) {
            computerDTO.setDiscontinuedDate(discontinued);
        }
        if (!companyId.isEmpty() && !companyId.equals("0")) {
            CompanyDTO companyDTO = new CompanyDTO.Builder().setIdCompany(companyId).build();
            computerDTO.setCompanyDTO(companyDTO);
        }
        if (ComputerValidator.dateFormatValidator(computerDTO.getIntroducedDate())
                && ComputerValidator.dateFormatValidator(computerDTO.getDiscontinuedDate())) {
            Computer computer = ComputerMapper.toComputer(computerDTO);
            if (computerService.allowedToCreateOrEdit(computer)) {
                computerService.create(computer);
                logger.info("computer creation ok");
            } else {
                logger.error("computer creation not allowed");
            }
        }
        return modelAndView;
    }
}
