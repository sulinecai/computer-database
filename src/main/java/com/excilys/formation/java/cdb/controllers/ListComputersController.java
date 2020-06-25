package com.excilys.formation.java.cdb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ListComputersController {
    { System.out.println("test controller"); }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        System.out.println("test");
        model.addAttribute("message", "Hello World!");
        return "index";
    }
}
