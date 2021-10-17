package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ColumnName;
import by.training.сonfectionery.model.dao.OrderDao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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
            INSERT INTO orders(date, phone, user_id, status_id)
            VALUES (?, ?, ?, ?);
            """;
    private static final String SQL_UPDATE_ORDER = """
            UPDATE orders
            SET date = ?, phone = ?, user_id = ?, status_id = ?
            WHERE orders.id = ?;
            """;
    private static final String SQL_UPDATE_ORDER_STATUS = """
            UPDATE orders
            SET status_id = ?
            WHERE orders.id = ?;
            """;
    private static final String SQL_ADD_PRODUCTS = """
            INSERT INTO orders_have_products(order_id, product_id, amount)
            VALUES (?, ?, ?);
            """;
    private static final String SQL_GET_NUMBER_OF_RECORDS = """
            SELECT COUNT(*) as count
            FROM orders
            """;

    private static final String SQL_FIND_PRODUCTS_IN_ORDER = """
            SELECT product_id, amount
            FROM orders_have_products
            WHERE order_id = ?
            """;

    @Override
    public Map<Integer, Integer> findProductsInOrders(int orderId) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCTS_IN_ORDER)){
            Map<Integer, Integer> map = new HashMap<>();
            statement.setInt(1, orderId);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()){
                    map.put(resultSet.getInt(1), resultSet.getInt(2));
                }
                return map;
            }
        }
        catch (SQLException e){
            throw new DaoException("Failed to find products in order", e);
        }
    }

    @Override
    public int getNumberOfRecords() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_NUMBER_OF_RECORDS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all products", e);
        }
    }

    @Override
    public List<Order> findAll(int i, int recordsPerPage) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS)) {
            List<Order> orders = new LinkedList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
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
    public List<Order> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS)) {
            List<Order> orders = new LinkedList<>();
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
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
    public boolean addProducts(int orderId, int productId, int amount) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_PRODUCTS)) {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.setInt(3, amount);
            boolean result = statement.executeUpdate() == 1;
            return result;
        } catch (SQLException e) {
            throw new DaoException("Failed to add products to order", e);
        }
    }

    @Override
    public boolean create(Order order) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(order.getDate()));
            statement.setString(2, order.getPhone());
            statement.setInt(3, order.getUserId());
            statement.setInt(4, order.getStatus().getId());
            boolean result = statement.executeUpdate() == 1;
            try(ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()) {
                    order.setId(resultSet.getInt(1));
                }
                return result;
            }
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
                .setPhone(resultSet.getString(ColumnName.PHONE))
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
