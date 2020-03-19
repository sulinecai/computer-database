package com.excilys.formation.java.cdb.persistence.daos;

import java.util.List;
import java.util.Optional;

public abstract class DAO<T> {

    public abstract List<T> getAll();

    public abstract Optional<T> findById(Long id);

}
