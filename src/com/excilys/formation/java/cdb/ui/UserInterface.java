package com.excilys.formation.java.cdb.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Page;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.ComputerService;

public class UserInterface {
	
	private static ComputerService computerService = new ComputerService();
	private static CompanyService companyService = new CompanyService();
	private static Scanner scanner = new Scanner(System.in);
	
	public void start() {
		
		boolean quit = false;

		while(!quit) {
		
			System.out.println("Computer Database \n");		
			System.out.println("Available features :");		
	
			System.out.println("1 - List computers");		
			System.out.println("2 - List companies");
			System.out.println("3 - Show computer details");
			System.out.println("4 - Create a computer");
			System.out.println("5 - Update a computer");
			System.out.println("6 - Delete a computer");
			System.out.println("7 - Quit");
			
			System.out.println("");		
	
			System.out.println("Enter your choice: ");
			int featureChoice = scanner.nextInt();
			scanner.nextLine();
					           	
			switch (featureChoice) {
				case 1:
					showAllComputers();
					break;
					
				case 2:
					showAllCompanies();
					break;
					
				case 3:
					System.out.println("Enter the id of a computer: ");
					Long computerId = scanner.nextLong();
					
					if (computerService.exist(computerId)) {
						Computer computer = computerService.findById(computerId);
						System.out.println(computer.toString());
					} else {
						System.out.println("The computer with the id " + computerId + " doesn't exit.");
					}
					
					backToMenu(); 
					
					break;
					
				case 4:
					boolean askId = false;
					Computer computer = inputComputer(askId);			
					
					if (computerService.allowedToCreate(computer)) {
						computerService.create(computer);
						System.out.println("Creation OK.");
					} else {
						System.out.println("Creation impossible.");
					}
					break;
					
				case 5:
					boolean needId = true;
					Computer computerUpd = inputComputer(needId);			
					
					if (computerService.exist(computerUpd.getIdComputer())) {
						computerService.update(computerUpd);
						System.out.println("Update OK.");
					} else {;
						System.out.println("Update impossible.");
					}
					break;
					
				case 6:
					System.out.println("Enter the id of the computer to delete: ");
					Long id = scanner.nextLong();
					boolean exist = computerService.exist(id);
					
					while(!exist) {
						System.out.println("The id doesn't exist.");
						System.out.println("Enter the id of an existing  computer : ");
						id = scanner.nextLong();
					}
					scanner.nextLine();
					computerService.delete(id);
					System.out.println("Delete OK.");
					break;
				case 7:
					quit = true;
					break;
					
				default:
					System.out.println("This feature doesn't exist.");
			}
		}

		scanner.close();
	}
	
	public Computer inputComputer(boolean needId) {
		Computer computer = new Computer();
		
		if (needId) {
			System.out.println("Enter the id of the computer: ");
			String id = scanner.nextLine();
			while(id.equals("")) {
				System.out.println("The id cannot be empty.");
				System.out.println("Enter the id of the computer : ");
				id = scanner.nextLine();
			}
			computer.setIdComputer(Long.valueOf(id));
		}

		System.out.println("Enter the name of the computer: ");
		String name = scanner.nextLine();
		while(name.equals("")) {
			System.out.println("The name cannot be empty.");
			System.out.println("Enter the name of the computer : ");
			name = scanner.nextLine();
		}
		computer.setName(name);
		
		System.out.println("Enter the introduced date of the computer in the format YYYY-MM-DD: (press <Enter> to skip)");
		String introduced = scanner.nextLine();
		
		if (!introduced.equals("")) {
			computer.setIntroducedDate(LocalDate.parse(introduced));
			System.out.println("Enter the discontinued date of the computer in the format YYYY-MM-DD: (press <Enter> to skip)");
			String discontinued = scanner.nextLine();
			if (!discontinued.equals("")) {
				computer.setDiscontinuedDate(LocalDate.parse(discontinued));
			}
		}				
		System.out.println("Enter the manufacturer company id:(press <Enter> to skip)");
		String companyId = scanner.nextLine();
		if (!companyId.equals("")) {
			computer.setCompany(new Company(Long.valueOf(companyId)));
		}
		
		return computer;
	}	
	
	public void showAllCompanies() {
		Page currentPage = new Page();
		boolean quit = false;
		List<Company> allCompanies = companyService.getAll();
		int numberOfCompanies = allCompanies.size();

		do {
			List<Company> allCompaniesbyPage = companyService.getAllByPage(currentPage);
			allCompaniesbyPage.forEach(cp -> System.out.println(cp.toString()));
			System.out.println("Page " + currentPage.getCurrentPage() + "/" + currentPage.getTotalPages(numberOfCompanies));
			System.out.println("(Enter 'q' to quit, 'p' to go to the previous page, 'n' to go to the next page.)");

			String input = scanner.nextLine();

			switch (input.toLowerCase()) {
			case "p":
				if (currentPage.hasPreviousPage()) {
					currentPage.previousPage();
				}
				break;
			case "n":
				if (currentPage.hasNextPage(numberOfCompanies)) {
					currentPage.nextPage();
				}
				break;
			case "q":
				quit = true;
				break;
			}
		} while (!quit);
	}
	
	public void showAllComputers() {
		Page currentPage = new Page();
		boolean quit = false;
		List<Computer> allComputers = computerService.getAll();
		int numberOfComputers = allComputers.size();

		do {
			List<Computer> allComputersbyPage = computerService.getAllByPage(currentPage);
			allComputersbyPage.forEach(cp -> System.out.println(cp.toString()));
			System.out.println("Page " + currentPage.getCurrentPage() + "/" + currentPage.getTotalPages(numberOfComputers));
			System.out.println("(Enter 'q' to quit, 'p' to go to the previous page, 'n' to go to the next page.)");

			String input = scanner.nextLine();

			switch (input.toLowerCase()) {
			case "p":
				if (currentPage.hasPreviousPage()) {
					currentPage.previousPage();
				}
				break;
			case "n":
				if (currentPage.hasNextPage(numberOfComputers)) {
					currentPage.nextPage();
				}
				break;
			case "q":
				quit = true;
				break;
			}
		} while (!quit);
	}
	
	public void backToMenu() {
		System.out.println("(Enter 'q' to quit this page and go to main page.)");
		boolean quit = false;
		do {
			String input = scanner.nextLine();
			if (input.toLowerCase().equals("q")) {
				quit = true;
			}
		} while (!quit);
	}
}