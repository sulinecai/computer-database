package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.services.ComputerService;


/**
 * Servlet implementation class ListComputers.
 */
@WebServlet("/ListComputers")
public class ListComputers extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ComputerService computerService = new ComputerService();
	    int nbComputers = computerService.getAll().size();
	    int computerPerPage = 10;
	    int currentPage = 1;
        if(request.getParameter("page") != null) {
        	currentPage = Integer.parseInt(request.getParameter("page"));
        }

        Page page = new Page(computerPerPage,currentPage);
        List<Computer> allComputers = computerService.getAllByPage(page);
        List<ComputerDTO> allComputerDTOs = new ArrayList<ComputerDTO>();
        for (Computer c : allComputers) {
            allComputerDTOs.add(ComputerMapper.toComputerDTO(c));
        }

        int nbPages = page.getTotalPages(nbComputers);
        int lastPageIndex = nbPages;
        if ((currentPage + 9 )< nbPages) {
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
		doGet(request, response);
	}
}
