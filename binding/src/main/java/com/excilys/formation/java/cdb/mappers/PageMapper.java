package com.excilys.formation.java.cdb.mappers;

import com.excilys.formation.java.cdb.dtos.PageDTO;
import com.excilys.formation.java.cdb.models.Page;

public class PageMapper {

    public static Page toPage (PageDTO dto) {
        return new Page(dto.getPageSize(), dto.getCurrentPage());
    }
}
