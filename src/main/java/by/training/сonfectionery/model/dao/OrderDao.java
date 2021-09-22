package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.DaoException;

import java.util.Optional;

public abstract class OrderDao extends AbstractDao<Integer, Order> {

    public abstract Optional<Order> findOrderByUserId(int userId) throws DaoException;

    public abstract boolean updateOrderStatus(Order order, Order.Status orderStatus) throws DaoException;
}
