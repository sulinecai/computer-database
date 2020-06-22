package com.excilys.formation.java.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.spring.SpringConfiguration;

/**
 * Servlet implementation class EditComputersServlet.
 */
@WebServlet("/EditComputer")
public class EditComputerServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

    CompanyService companyService = SpringConfiguration.CONTEXT.getBean("companyService", CompanyService.class);
    ComputerService computerService = SpringConfiguration.CONTEXT.getBean("computerService", ComputerService.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Company> allCompanies = companyService.getAll();
        List<CompanyDTO> allCompanyDTOs = new ArrayList<CompanyDTO>();
        for (Company c : allCompanies) {
            allCompanyDTOs.add(CompanyMapper.toCompanyDTO(c));
        }

        request.setAttribute("companies", allCompanyDTOs);

        if (!request.getParameter("idComputer").isEmpty() && !request.getParameter("idComputer").equals("0")) {
            Long idComputer = Long.valueOf(request.getParameter("idComputer"));
            if (computerService.exist(idComputer)) {
                Computer computer = computerService.findById(idComputer);
                ComputerDTO dto = ComputerMapper.toComputerDTO(computer);
                request.setAttribute("computer", dto);
            }
        }

        request.getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerDTO computerDTO = new ComputerDTO();

        if (!request.getParameter("idComputer").isEmpty()) {
            computerDTO.setIdComputer(request.getParameter("idComputer"));
            if (!request.getParameter("computerName").isEmpty()) {
                computerDTO.setName(request.getParameter("computerName"));
            }
            if (!request.getParameter("introduced").isEmpty()) {
                computerDTO.setIntroducedDate(request.getParameter("introduced"));
            }
            if (!request.getParameter("discontinued").isEmpty()) {
                computerDTO.setDiscontinuedDate(request.getParameter("discontinued"));
            }
            if (!request.getParameter("companyId").isEmpty() && !request.getParameter("companyId").equals("0")) {
                CompanyDTO companyDTO = new CompanyDTO.Builder().setIdCompany(request.getParameter("companyId")).build();
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

        doGet(request, response);
    }
}
