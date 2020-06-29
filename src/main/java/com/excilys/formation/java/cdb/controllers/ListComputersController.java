package com.excilys.formation.java.cdb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.services.ComputerService;

@Controller
public class ListComputersController {

    @Autowired
    private ComputerService computerService;

    static int computerPerPage = 10;
    static int nbComputers;

    @GetMapping(value = "ListComputers")
    public ModelAndView listComputers(@RequestParam(required = false, name = "search") String searchValue,
            @RequestParam(required = false, name = "page") Integer currentPage,
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            @RequestParam(required = false, name = "search") String searcdhValue,
            @RequestParam(required = false, name = "orderBy") String orderBy) {

        ModelAndView modelAndView = new ModelAndView("dashboard");
        nbComputers = computerService.getNumberComputers();
        if (currentPage == null) {
            currentPage = 1;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize != null) {
            computerPerPage = pageSize;
        }
        Page page = new Page(computerPerPage, currentPage);
        List<Computer> allComputers = new ArrayList<Computer>();
        if (searchValue == null && orderBy == null) {
            allComputers = computerService.getAllByPage(page);
        } else if (searchValue == null && orderBy != null) {
            allComputers = computerService.orderBy(page, orderBy);
            modelAndView.addObject("orderBy", orderBy);
        } else {
            allComputers = computerService.findByNameByPage(searchValue, page);
            modelAndView.addObject("search", searchValue);
            nbComputers = computerService.findAllByName(searchValue).size();
        }
        int nbPages = page.getTotalPages(nbComputers);
        if (currentPage > nbPages) {
            currentPage = nbPages;
            page.setCurrentPage(currentPage);
        }

        List<ComputerDTO> allComputerDTOs = new ArrayList<ComputerDTO>();
        for (Computer c : allComputers) {
            allComputerDTOs.add(ComputerMapper.toComputerDTO(c));
        }
        int lastPageIndex = nbPages;
        if ((currentPage + 9) < nbPages) {
            lastPageIndex = currentPage + 9;
        }
        modelAndView.addObject("computers", allComputerDTOs);
        modelAndView.addObject("nbComputers", nbComputers);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("nbPages", nbPages);
        modelAndView.addObject("lastPageIndex", lastPageIndex);
        return modelAndView;
    }

    @PostMapping(value = "/deleteComputer")
    public ModelAndView deleteComputer(@RequestParam List<Long> selection,
            @RequestParam(required = false) Integer currentPage) {
        ModelAndView modelAndView = new ModelAndView("redirect:/ListComputers");
        for (Long computerId : selection) {
            computerService.delete(computerId);
        }
        currentPage = pageNumberToRedirect(currentPage, selection.size());
        modelAndView.addObject("page", currentPage);
        return modelAndView;
    }

    /**
     * Get the page name to redirect to after deleting computers.
     *
     * @param currentPage
     * @param nbComputerToDelete
     * @return page
     */
    private int pageNumberToRedirect(Integer currentPage, Integer nbComputerToDelete) {
        if (currentPage == null) {
            currentPage = 1;
        }
        int pageToGo = currentPage;
        int nbPage = new Page(computerPerPage, currentPage).getTotalPages(nbComputers);
        if (currentPage < 1) {
            pageToGo = 1;
        } else if (currentPage == nbPage && currentPage != 1) {
            int nbCpLastPage = nbComputers % computerPerPage;
            if (nbComputerToDelete == nbCpLastPage) {
                pageToGo--;
            }
        }
        return pageToGo;
    }
}
