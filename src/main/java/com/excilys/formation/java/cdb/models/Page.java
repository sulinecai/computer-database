package com.excilys.formation.java.cdb.models;

public class Page {
	
	private int currentPage;
	private int linesPerPage;

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_LINES_PER_PAGE = 20;
	
	public Page() {
		this.currentPage = DEFAULT_PAGE;
		this.linesPerPage = DEFAULT_LINES_PER_PAGE;
	}
	
	public Page(int maxLine, int currentpage) {
		this.currentPage = currentpage;
		this.linesPerPage = maxLine;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentpage) {
		this.currentPage = currentpage;
	}
	
	public int getMaxLine() {
		return linesPerPage;
	}
	
	public void setMaxLine(int maxLine) {
		this.linesPerPage = maxLine;
	}
	
	public int getTotalPages(int numberOfEntries) {
		return (int)Math.ceil((double)numberOfEntries/linesPerPage);
	}
		
	public int getPageFirstLine() {
		return linesPerPage * (currentPage-1);
	}
	
	public void nextPage() {
		currentPage ++;
	}
	
	public void previousPage() {
		currentPage --;
	}
	
	public boolean hasNextPage(int numberOfEntries) {
		boolean result = currentPage < getTotalPages(numberOfEntries) ? true : false;
		return result;
	}
	
	public boolean hasPreviousPage() {
		boolean result = currentPage >1 ? true : false;
		return result;
	}

}
