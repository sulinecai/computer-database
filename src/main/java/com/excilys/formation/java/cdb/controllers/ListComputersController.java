package com.excilys.formation.java.cdb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.dtos.DashboardDTO;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.services.ComputerService;

@Controller
public class ListComputersController {

    private ComputerService computerService;

    public ListComputersController (ComputerService computerService) {
        this.computerService = computerService;
    }

    static int computerPerPage = 10;
    static int nbComputers;

    @GetMapping(value = {"ListComputers", "/"})
    public ModelAndView listComputers(DashboardDTO dashboardDTO) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        nbComputers = computerService.getNumberComputers();
        if (dashboardDTO.getPageSize() != null) {
            computerPerPage = dashboardDTO.getPageSize();
        }
        Page page = new Page(computerPerPage, dashboardDTO.getPage());
        List<Computer> allComputers = new ArrayList<Computer>();
        if (dashboardDTO.getSearch() == null) {
            allComputers = computerService.orderBy(page, dashboardDTO.getOrderBy());
            modelAndView.addObject("orderBy", dashboardDTO.getOrderBy());
        } else {
            allComputers = computerService.findByNameByPage(dashboardDTO.getSearch(), page);
            modelAndView.addObject("search", dashboardDTO.getSearch());
            nbComputers = computerService.findAllByName(dashboardDTO.getSearch()).size();
        }
        int nbPages = page.getTotalPages(nbComputers);
        if (dashboardDTO.getPage() > nbPages) {
            dashboardDTO.setPage(nbPages);
            page.setCurrentPage(dashboardDTO.getPage());
        }

        List<ComputerDTO> allComputerDTOs = new ArrayList<ComputerDTO>();
        for (Computer c : allComputers) {
            allComputerDTOs.add(ComputerMapper.toComputerDTO(c));
        }
        int lastPageIndex = nbPages;
        if ((dashboardDTO.getPage() + 9) < nbPages) {
            lastPageIndex = dashboardDTO.getPage() + 9;
        }
        modelAndView.addObject("computers", allComputerDTOs);
        modelAndView.addObject("nbComputers", nbComputers);
        modelAndView.addObject("currentPage", dashboardDTO.getPage());
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
