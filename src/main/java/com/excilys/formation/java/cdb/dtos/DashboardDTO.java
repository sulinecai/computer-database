package com.excilys.formation.java.cdb.dtos;

public class DashboardDTO {

    private String search;
    private Integer page = 1;
    private Integer pageSize;
    private String orderBy = "";

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        System.out.println(orderBy);
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "DashboardDTO [search=" + search + ", page=" + page + ", pageSize=" + pageSize + ", orderBy=" + orderBy
                + "]";
    }
}
