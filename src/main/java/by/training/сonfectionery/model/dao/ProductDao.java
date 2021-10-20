package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class ProductDao extends AbstractDao<Integer, Product> {

    public abstract int getNumberOfRecords() throws DaoException;

    public abstract List<Product> findProductByProductTypeId(int offset, int numberOfRecords, String[] productTypeId, int sortBy) throws DaoException;

    public abstract List<Product> findAll(int i, int recordsPerPage) throws DaoException;

    public abstract int getNumberOfRecords(String[] productTypeId) throws DaoException;
}