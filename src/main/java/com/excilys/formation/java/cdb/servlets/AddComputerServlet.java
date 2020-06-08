package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;


/**
 * Servlet implementation class AddComputerServlet.
 */
@WebServlet("/AddComputer")
public class AddComputerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CompanyService companyService = new CompanyService();
        List<Company> allCompanies = companyService.getAll();
        List<CompanyDTO> allCompanyDTOs = new ArrayList<CompanyDTO>();
        for (Company c : allCompanies) {
            allCompanyDTOs.add(CompanyMapper.toCompanyDTO(c));
        }

        request.setAttribute("companies", allCompanyDTOs);

        request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ComputerDTO computerDTO = new ComputerDTO(request.getParameter("computerName"));
	    if (!request.getParameter("introduced").isEmpty()) {
	        computerDTO.setIntroducedDate(request.getParameter("introduced"));
	    }
        if (!request.getParameter("introduced").isEmpty()) {
            computerDTO.setDiscontinuedDate(request.getParameter("discontinued"));
        }
        if (!request.getParameter("companyId").isEmpty() && !request.getParameter("companyId").equals("0")) {
            System.out.println("test "+request.getParameter("companyId"));
            CompanyDTO companyDTO = new CompanyDTO(request.getParameter("companyId"));
            computerDTO.setCompanyDTO(companyDTO);
        }
        ComputerService computerService = new ComputerService();
        Computer computer = ComputerMapper.toComputer(computerDTO);
        if (computerService.allowedToCreate(computer)) {
            computerService.create(computer);
            System.out.println("creation ok");

        } else {
            System.out.println("creation impossible");
        }

		doGet(request, response);
	}
}
