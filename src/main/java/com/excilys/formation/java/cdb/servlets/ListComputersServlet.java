package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.services.ComputerService;

/**
 * Servlet implementation class ListComputersServlet.
 */
//@WebServlet("/ListComputers")
public class ListComputersServlet extends HttpServlet {

    //ComputerService computerService = SpringConfiguration.CONTEXT.getBean(ComputerService.class);
	@Autowired
	private ComputerService computerService;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

    private static final long serialVersionUID = 1L;
    static int computerPerPage = 10;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int nbComputers = computerService.getNumberComputers();
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("pageSize") != null) {
            computerPerPage = Integer.parseInt(request.getParameter("pageSize"));
            request.setAttribute("pageSize", computerPerPage);
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        Page page = new Page(computerPerPage, currentPage);

        List<Computer> allComputers = new ArrayList<Computer>();
        if (request.getParameter("search") == null && request.getParameter("orderBy") == null) {
            allComputers = computerService.getAllByPage(page);
        } else if (request.getParameter("search") == null && request.getParameter("orderBy") != null) {
            allComputers = computerService.orderBy(page, request.getParameter("orderBy"));
            request.setAttribute("orderBy", request.getParameter("orderBy"));
        } else {
            String search = request.getParameter("search");
            allComputers = computerService.findByNameByPage(search, page);
            request.setAttribute("search", search);
            nbComputers = computerService.findAllByName(search).size();
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

        request.setAttribute("computers", allComputerDTOs);
        request.setAttribute("nbComputers", nbComputers);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("nbPages", nbPages);
        request.setAttribute("lastPageIndex", lastPageIndex);
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("selection") != null && !request.getParameter("selection").isEmpty()) {
            String[] computerIdsToDelete = request.getParameter("selection").split(",");

            for (String computerId : computerIdsToDelete) {
                computerService.delete(Long.valueOf(computerId));
            }
        }

        doGet(request, response);
    }
}
