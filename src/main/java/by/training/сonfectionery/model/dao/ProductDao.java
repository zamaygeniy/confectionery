package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.DaoException;

import java.util.Optional;

public abstract class ProductDao extends AbstractDao<Integer, Product> {

    public abstract Optional<Product> findProductByProductTypeId(int productTypeID) throws DaoException;

    public abstract boolean updateProductNumberInStock(Product product, int newNumberInStock) throws DaoException;

    public abstract boolean addProductToCart(Product product, int numberOfProduct, Order order) throws DaoException;
}