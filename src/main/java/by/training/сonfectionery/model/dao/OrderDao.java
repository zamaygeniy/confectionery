package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class OrderDao extends AbstractDao<Integer, Order> {
    public abstract Optional<Order> findOrderByUserId(int userId) throws DaoException;
    public abstract boolean addProducts(int orderId, int productsId, int amount) throws DaoException;
    public abstract int getNumberOfRecords() throws DaoException;
    public abstract List<Order> findAll(int i, int recordsPerPage) throws DaoException;
    public abstract boolean updateOrderStatus(Order order, Order.Status orderStatus) throws DaoException;
    public abstract Map<Integer, Integer> findProductsInOrders(int orderId) throws DaoException;
}
