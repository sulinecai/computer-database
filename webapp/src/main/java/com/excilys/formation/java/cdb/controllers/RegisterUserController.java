package com.excilys.formation.java.cdb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.java.cdb.dtos.UserDTO;
import com.excilys.formation.java.cdb.services.UserService;

@Controller
@RequestMapping("/RegisterUser")
public class RegisterUserController {

    private static Logger logger = LoggerFactory.getLogger(RegisterUserController.class);

    private UserService userService;

    public RegisterUserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showInfo() {
        return "registerUser";
    }

    @PostMapping
    public ModelAndView registerUser(UserDTO userDTO) {
        System.out.println(userDTO);
//        if (companyDTO.getIdCompany() != null && !companyDTO.getIdCompany().equals("0")){
//            computerDTO.setCompanyDTO(companyDTO);
//        }
//        if (ComputerValidator.dateFormatValidator(computerDTO.getIntroducedDate())
//                && ComputerValidator.dateFormatValidator(computerDTO.getDiscontinuedDate())) {
//            Computer computer = ComputerMapper.toComputer(computerDTO);
//            if (computerService.allowedToCreateOrEdit(computer)) {
//                computerService.create(computer);
//                logger.info("computer creation ok");
//            } else {
//                logger.error("computer creation not allowed");
//            }
//        }
        return new ModelAndView("redirect:/ListComputers");
    }
}
