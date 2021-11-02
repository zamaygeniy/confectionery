package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.model.domain.Entity;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.connection.ProxyConnection;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<K, T extends Entity> {
    protected ProxyConnection connection;

    public abstract List<T> findAll() throws DaoException;

    public abstract Optional<T> findById(K id) throws DaoException;

    public abstract void deleteById(K id) throws DaoException;

    public abstract boolean create(T t) throws DaoException;

    public abstract T update(T t) throws DaoException;

    public void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }


}
