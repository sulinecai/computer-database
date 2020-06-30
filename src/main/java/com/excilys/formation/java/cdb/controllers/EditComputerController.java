package com.excilys.formation.java.cdb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("/EditComputer")
public class EditComputerController {

    private static Logger logger = LoggerFactory.getLogger(EditComputerController.class);

    private CompanyService companyService;

    private ComputerService computerService;

    public EditComputerController (CompanyService companyService, ComputerService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    @GetMapping
    public ModelAndView showInfo(
            @RequestParam String idComputer,
            @RequestParam (required = false)Integer page) {
        ModelAndView modelAndView = new ModelAndView("editComputer");

        List<Company> allCompanies = companyService.getAll();
        List<CompanyDTO> allCompanyDTOs = new ArrayList<CompanyDTO>();
        for (Company c : allCompanies) {
            allCompanyDTOs.add(CompanyMapper.toCompanyDTO(c));
        }
        modelAndView.addObject("companies", allCompanyDTOs);
        if (!idComputer.isEmpty() && !idComputer.equals("0")) {
            Long idCompu = Long.valueOf(idComputer);
            if (computerService.exist(idCompu)) {
                Computer computer = computerService.findById(idCompu);
                ComputerDTO dto = ComputerMapper.toComputerDTO(computer);
                modelAndView.addObject("computer", dto);
            }
        }
        if (page == null) {
            page = 1;
        }
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView editComputer(
            @RequestParam String idComputer,
            @RequestParam String computerName,
            @RequestParam String introduced,
            @RequestParam String discontinued,
            @RequestParam String companyId,
            @RequestParam Integer currentPage) {
        ModelAndView modelAndView = new ModelAndView("redirect:/ListComputers");
        ComputerDTO computerDTO = new ComputerDTO();

        if (!idComputer.isEmpty()) {
            computerDTO.setIdComputer(idComputer);
            if (!computerName.isEmpty()) {
                computerDTO.setName(computerName);
            }
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
        }
        Computer computer = ComputerMapper.toComputer(computerDTO);
        if (computerService.allowedToCreateOrEdit(computer)) {
            computerService.update(computer);
            logger.info("computer update ok");
        } else {
            logger.info("computer update not allowed");
        }
        modelAndView.addObject("page", currentPage);
        return modelAndView;
    }
}
