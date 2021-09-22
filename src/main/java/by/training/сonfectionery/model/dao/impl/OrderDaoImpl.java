package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ColumnName;
import by.training.сonfectionery.model.dao.OrderDao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class OrderDaoImpl extends OrderDao {

    private static final String SQL_FIND_ALL_ORDERS = """
             SELECT orders.id, date, phone, user_id, status
             FROM orders
             JOIN order_status ON orders.status_id = order_status.id
            """;
    private static final String SQL_FIND_ORDER_BY_ID = """
             SELECT orders.id, date, phone, user_id, status
             FROM orders
             JOIN order_status ON orders.status_id = order_status.id
             WHERE id = ?;
            """;
    private static final String SQL_FIND_ORDER_BY_USER_ID = """
             SELECT orders.id, date, phone, user_id, status
             FROM orders
             JOIN order_status ON orders.status_id = order_status.id
             WHERE user_id = ?;
            """;
    private static final String SQL_DELETE_ORDER_BY_ID = """
            DELETE FROM orders WHERE id = ?;
            """;
    private static final String SQL_CREATE_ORDER = """
            INSERT INTO orders(id, date, phone, user_id, status)
            VALUES (?, ?, ?, ?, ?);
            """;
    private static final String SQL_UPDATE_ORDER = """
            UPDATE orders
            SET date = ?, phone = ?, user_id = ?, status = ?
            WHERE orders.id = ?;
            """;
    private static final String SQL_UPDATE_ORDER_STATUS = """
            UPDATE orders
            SET status_id = ?
            WHERE orders.id = ?;
            """;


    @Override
    public List<Order> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS)) {
            List<Order> orders = new LinkedList<>();
            System.out.println("1");
            try(ResultSet resultSet = statement.executeQuery()){
                System.out.println("1");
                while (resultSet.next()) {
                    System.out.println("1");
                    Order order = buildOrder(resultSet);
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all orders", e);
        }
    }

    @Override
    public Optional<Order> findById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = buildOrder(resultSet);
                return Optional.of(order);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find order by id", e);
        }
    }

    @Override
    public boolean deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete order by id", e);
        }
    }

    @Override
    public boolean create(Order order) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(order.getDate()));
            statement.setInt(2, order.getUserId());
            statement.setInt(3, order.getStatus().getId());
            boolean result = statement.executeUpdate() == 1;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getInt(1));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Failed to create order", e);
        }
    }

    @Override
    public Order update(Order order) throws DaoException {
        Order oldOrder = findById(order.getId()).
                orElseThrow(() -> new DaoException("Failed to update order, order Id was not found"));
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            statement.setDate(1, Date.valueOf(order.getDate()));
            statement.setInt(2, order.getUserId());
            statement.setInt(3, order.getStatus().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update order", e);
        }
        return oldOrder;
    }


    private Order buildOrder(ResultSet resultSet) throws SQLException {

        return new Order.OrderBuilder()
                .setId(resultSet.getInt(ColumnName.ID))
                .setDate(resultSet.getDate(ColumnName.DATE).toLocalDate())
                .setUserId(resultSet.getInt(ColumnName.USER_ID))
                .setOrderPhone(resultSet.getString(ColumnName.PHONE))
                .setStatus(Order.Status.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase(Locale.ROOT)))
                .createOrder();
    }

    @Override
    public Optional<Order> findOrderByUserId(int userId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_USER_ID)) {
            statement.setInt(1, userId);

            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    Order order = buildOrder(resultSet);
                    return Optional.of(order);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to find order by user id", e);
        }
    }

    @Override
    public boolean updateOrderStatus(Order order, Order.Status orderStatus) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS)) {
            statement.setInt(1, orderStatus.getId());
            statement.setInt(2, order.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to update order status", e);
        }
    }
}
