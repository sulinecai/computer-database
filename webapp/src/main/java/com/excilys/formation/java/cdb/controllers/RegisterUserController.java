package com.excilys.formation.java.cdb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.java.cdb.dtos.UserDTO;
import com.excilys.formation.java.cdb.mappers.UserMapper;
import com.excilys.formation.java.cdb.services.UserService;

@Controller
@RequestMapping("/RegisterUser")
public class RegisterUserController {

    private static Logger logger = LoggerFactory.getLogger(RegisterUserController.class);

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public RegisterUserController (UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showInfo() {
        return "registerUser";
    }

    @PostMapping
    public ModelAndView registerUser(UserDTO userDTO, String confirm) {
        logger.info(userDTO.toString());
        logger.info(confirm);
        if (!userDTO.getUsername().isEmpty() && !userDTO.getPassword().isEmpty() && userDTO.getPassword().equals(confirm)) {
            userDTO.setPassword(passwordEncoder.encode(confirm));

            userService.create(UserMapper.toUser(userDTO));
        }
        return new ModelAndView("redirect:/ListComputers");
    }
}
