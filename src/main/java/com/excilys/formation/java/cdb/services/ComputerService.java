package com.excilys.formation.java.cdb.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;

public class ComputerService {

    private static ComputerService computerService;

    private ComputerService() {
    }

    public static synchronized ComputerService getInstance() {
        if (computerService == null) {
            computerService = new ComputerService();
        }
        return computerService;
    }

    private ComputerDAO computerDAO = ComputerDAO.getInstance();
    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    private static Logger logger = LoggerFactory.getLogger(ComputerService.class);

    public List<Computer> getAll() {
        return computerDAO.getAll();
    }

    public List<Computer> getAllByPage(Page page) {
        return computerDAO.getAllByPage(page);
    }

    public Computer findById(Long id) {
        return computerDAO.findById(id).get();
    }

    public List<Computer> findByNameByPage(String name, Page page) {
        return computerDAO.findByNameByPage(name, page);
    }

    public List<Computer> findAllByName(String name) {
        return computerDAO.findAllByName(name);
    }

    public void create(Computer computer) {
        computerDAO.create(computer);
    }

    public boolean allowedToCreateOrEdit(Computer computer) {
        boolean allowed = true;
        if (computer.getName() == null || computer.getName().isEmpty()) {
            logger.error("computer name is required");
            allowed = false;
        } else if (computer.getDiscontinuedDate() != null) {
            if (computer.getIntroducedDate() == null) {
                allowed = false;
                logger.error("introduced date is null");
            } else if (computer.getDiscontinuedDate().isBefore(computer.getIntroducedDate())) {
                allowed = false;
                logger.error("discontinued is before intro");
            }
            if (computer.getCompany() != null) {
                if (!companyDAO.findById(computer.getCompany().getIdCompany()).isPresent()) {
                    allowed = false;
                    logger.error("company does not exist");
                }
            }
        }
        return allowed;
    }

    public void update(Computer computer) {
        computerDAO.update(computer);
    }

    public void delete(Long id) {
        computerDAO.delete(id);
    }

    public boolean exist(Long id) {
        return computerDAO.findById(id).isPresent();
    }

    public List<Computer> orderByComputerNameAsc(Page page) {
        return computerDAO.orderByComputerNameAsc(page);
    }

    public List<Computer> orderByComputerNameDesc(Page page) {
        return computerDAO.orderByComputerNameDesc(page);
    }

}
