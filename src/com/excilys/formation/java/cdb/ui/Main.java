package com.excilys.formation.java.cdb.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.ComputerService;

public class Main {
	
	private static ComputerService cService = new ComputerService();

	public static void main(String[] args) {
		try {
			Optional<Computer> cp = cService.findById(1);
			System.out.println(cp.toString());
			
			boolean createSuccess = cService.create(new Computer("test",LocalDate.parse("1983-12-01"),LocalDate.parse("1983-12-01"),null));
			System.out.println(createSuccess);
			
			boolean updateSuccess = cService.update(new Computer(590,"hello",LocalDate.parse("1981-11-01"),LocalDate.parse("1984-02-02"),null));
			System.out.println(updateSuccess);
			
			Optional<Computer> cp1 = cService.findById(590);
			System.out.println(cp1.toString());

			
//			List<Computer> allComputer = cService.getAll();
//			for (Computer c : allComputer) {
//				System.out.println(c.toString());
//			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}