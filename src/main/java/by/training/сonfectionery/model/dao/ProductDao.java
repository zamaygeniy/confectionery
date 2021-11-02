package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.model.domain.Product;
import by.training.сonfectionery.exception.DaoException;

import java.util.List;

public abstract class ProductDao extends AbstractDao<Integer, Product> {

    public abstract int getNumberOfRecords() throws DaoException;

    public abstract List<Product> findProductByProductTypeId(int offset, int recordsPerPage, List<Integer> productTypeId, int sortBy) throws DaoException;

    public abstract List<Product> findAll(int i, int recordsPerPage) throws DaoException;

    public abstract int getNumberOfRecords(List<Integer> productTypeId) throws DaoException;
}