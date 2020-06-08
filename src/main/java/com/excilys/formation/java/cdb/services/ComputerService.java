package com.excilys.formation.java.cdb.services;

import java.util.List;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;

public class ComputerService implements Service<Computer> {

    private ComputerDAO computerDAO = ComputerDAO.getInstance();
    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    @Override
    public List<Computer> getAll() {
        return computerDAO.getAll();
    }

    public List<Computer> getAllByPage(Page page) {
        return computerDAO.getAllByPage(page);
    }

    @Override
    public Computer findById(Long id) {
        return computerDAO.findById(id).get();
    }

    public void create(Computer computer) {
        computerDAO.create(computer);
    }

    public boolean allowedToCreate(Computer computer) {
        boolean allowed = true;
        if (computer.getName() == null) {
            System.out.println("computer name is null");
            allowed = false;
        } else if (computer.getDiscontinuedDate() != null) {
            if (computer.getIntroducedDate() == null) {
                allowed = false;
                System.out.println("introduced date is null");
            } else if (computer.getDiscontinuedDate().isBefore(computer.getIntroducedDate())) {
                allowed = false;
                System.out.println("discontinued is before intro");
            }
            if (computer.getCompany() != null) {
                if (!companyDAO.findById(computer.getCompany().getIdCompany()).isPresent()) {
                    allowed = false;
                    System.out.println("company does not exist");
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

    @Override
    public boolean exist(Long id) {
        return computerDAO.findById(id).isPresent();
    }
}
