package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class OrderDao extends AbstractDao<Integer, Order> {

    public abstract Optional<Order> findOrderByUserId(int userId) throws DaoException;

    public abstract void addProducts(int orderId, int productsId, int amount) throws DaoException;

    public abstract int getNumberOfRecords() throws DaoException;

    public abstract int getNumberOfRecords(int userId) throws DaoException;

    public abstract int getNumberOfRecords(List<Integer> statusId) throws DaoException;

    public abstract int getNumberOfRecords(int userId, List<Integer> statusId) throws DaoException;

    public abstract List<Order> findOrders(int offset, int recordsPerPage) throws DaoException;

    public abstract List<Order> findOrders(int offset, int recordsPerPage, int userId) throws DaoException;

    public abstract List<Order> findOrders(int offset, int recordsPerPage, List<Integer> statusId) throws DaoException;

    public abstract List<Order> findOrders(int offset, int recordsPerPage, int userId, List<Integer> statusId) throws DaoException;

    public abstract void updateOrderStatus(int id, Order.Status orderStatus) throws DaoException;

    public abstract Map<Integer, Integer> findProductsInOrders(int orderId) throws DaoException;
}
