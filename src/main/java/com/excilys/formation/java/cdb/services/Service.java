package com.excilys.formation.java.cdb.services;

import java.util.List;

public interface Service<T> {

    List<T> getAll();

    T findById(Long id);

    boolean exist(Long id);

}
