package br.org.irede.fintrack.dao;

import java.sql.SQLException;

public interface RepositorioGenerico<T, ID> {
    void save(T entidade) throws SQLException;

    T findById(ID id) throws SQLException;

    void delete(ID id) throws SQLException;
}