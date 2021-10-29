package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ColumnName;
import by.training.сonfectionery.model.dao.OrderDao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OrderDaoImpl extends OrderDao {

    private static final String SQL_FIND_ALL_ORDERS = """
             SELECT orders.id, date, phone, cost, user_id, status
             FROM orders
             JOIN order_status ON orders.status_id = order_status.id
            """;
    private static final String SQL_FIND_ORDER_BY_ID = """
             SELECT orders.id, date, phone, cost, user_id, status
             FROM orders
             JOIN order_status ON orders.status_id = order_status.id
             WHERE id = ?;
            """;
    private static final String SQL_FIND_ORDER_BY_USER_ID = """
             SELECT orders.id, date, phone, cost, user_id, status
             FROM orders
             JOIN order_status ON orders.status_id = order_status.id
             WHERE user_id = ?;
            """;

    private static final String SQL_DELETE_ORDER_BY_ID = """
            DELETE FROM orders WHERE id = ?;
            """;
    private static final String SQL_CREATE_ORDER = """
            INSERT INTO orders(date, phone, cost, user_id, status_id)
            VALUES (?, ?, ?, ?, ?);
            """;
    private static final String SQL_UPDATE_ORDER = """
            UPDATE orders
            SET date = ?, phone = ?, cost = ?, user_id = ?, status_id = ?
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
    public List<Order> findOrders(int offset, int recordsPerPage, int userId, List<Integer> statusId) throws DaoException {
        String query = SQL_FIND_ALL_ORDERS;
        int key = -1;
        if (statusId != null && userId > 0) {
            query = query + "WHERE status_id IN (";
            for (int i = 0; i < statusId.size(); i++) {
                query = query + "?,";
            }
            query = query.substring(0, query.length() - 1) + ") AND user_id = ?";
            key = 0;
        } else if (userId > 0) {
            query = query + "WHERE user_id = ?";
            key = 1;
        } else if (statusId != null) {
            query = query + "WHERE status_id IN (";
            for (int i = 0; i < statusId.size(); i++) {
                query = query + "?,";
            }
            query = query.substring(0, query.length() - 1) + ")";
            key = 2;
        }
        query = query + "\nLIMIT ?, ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            List<Order> orders = new ArrayList<>();
            switch (key) {
                case 0:
                    int i;
                    for (i = 0; i < statusId.size(); i++) {
                        statement.setInt(i + 1, statusId.get(i));
                    }
                    statement.setInt(i + 1, userId);
                    statement.setInt(i + 2, offset);
                    statement.setInt(i + 3, recordsPerPage);
                    break;
                case 1:
                    statement.setInt(1, userId);
                    statement.setInt(2, offset);
                    statement.setInt(3, recordsPerPage);
                    break;
                case 2:
                    for (i = 0; i < statusId.size(); i++) {
                        statement.setInt(i + 1, statusId.get(i));
                    }
                    statement.setInt(i + 1, offset);
                    statement.setInt(i + 2, recordsPerPage);
                    break;
                default:
                    statement.setInt(1, offset);
                    statement.setInt(2, recordsPerPage);
                    break;
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = buildOrder(resultSet);
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all orders with status", e);
        }
    }

    @Override
    public Map<Integer, Integer> findProductsInOrders(int orderId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCTS_IN_ORDER)) {
            Map<Integer, Integer> map = new HashMap<>();
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    map.put(resultSet.getInt(1), resultSet.getInt(2));
                }
                return map;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find products in order", e);
        }
    }


    @Override
    public int getNumberOfRecords(int userId, List<Integer> statusId) throws DaoException {
        String query = SQL_GET_NUMBER_OF_RECORDS;
        int key = -1;
        if (statusId != null && userId > 0) {
            query = query + "WHERE status_id IN (";
            for (int i = 0; i < statusId.size(); i++) {
                query = query + "?,";
            }
            query = query.substring(0, query.length() - 1) + ") AND user_id = ?";
            key = 0;
        } else if (userId > 0) {
            query = query + "WHERE user_id = ?";
            key = 1;
        } else if (statusId != null) {
            query = query + "WHERE status_id IN (";
            for (int i = 0; i < statusId.size(); i++) {
                query = query + "?,";
            }
            query = query.substring(0, query.length() - 1) + ")";
            key = 2;
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            switch (key) {
                case 0:
                    int i;
                    for (i = 0; i < statusId.size(); i++) {
                        statement.setInt(i + 1, statusId.get(i));
                    }
                    statement.setInt(i + 1, userId);
                    break;
                case 1:
                    statement.setInt(1, userId);
                    break;
                case 2:
                    for (i = 0; i < statusId.size(); i++) {
                        statement.setInt(i + 1, statusId.get(i));
                    }
                    break;
                default:
                    break;
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find number of orders", e);
        }
    }

    @Override
    public List<Order> findOrders(int offset, int recordsPerPage) throws DaoException {
        return findOrders(offset, recordsPerPage, -1, null);
    }

    @Override
    public List<Order> findOrders(int offset, int recordsPerPage, int userId) throws DaoException {
        return findOrders(offset, recordsPerPage, userId, null);
    }

    @Override
    public List<Order> findOrders(int offset, int recordsPerPage, List<Integer> statusId) throws DaoException {
        return findOrders(offset, recordsPerPage, -1, statusId);
    }

    @Override
    public int getNumberOfRecords() throws DaoException {
        return getNumberOfRecords(-1, null);
    }

    @Override
    public int getNumberOfRecords(int userId) throws DaoException {
        return getNumberOfRecords(userId, null);
    }

    @Override
    public int getNumberOfRecords(List<Integer> statusId) throws DaoException {
        return getNumberOfRecords(-1, statusId);
    }

    @Override
    public List<Order> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS)) {
            List<Order> orders = new ArrayList<>();
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
    public void addProducts(int orderId, int productId, int amount) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_PRODUCTS)) {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.setInt(3, amount);
            boolean result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to add products to order", e);
        }
    }

    @Override
    public boolean create(Order order) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(order.getDate()));
            statement.setString(2, order.getPhone());
            statement.setDouble(3, order.getCost());
            statement.setInt(4, order.getUserId());
            statement.setInt(5, order.getStatus().getId());
            boolean result = statement.executeUpdate() == 1;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
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
            statement.setString(2, order.getPhone());
            statement.setDouble(3,order.getCost());
            statement.setInt(4, order.getUserId());
            statement.setInt(5, order.getStatus().getId());
            statement.setInt(6, order.getId());
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
                .setCost(resultSet.getDouble(ColumnName.COST))
                .setStatus(Order.Status.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase(Locale.ROOT)))
                .createOrder();
    }

    @Override
    public Optional<Order> findOrderByUserId(int userId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_USER_ID)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
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
    public void updateOrderStatus(int id, Order.Status orderStatus) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS)) {
            statement.setInt(1, orderStatus.getId());
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update order status", e);
        }
    }


}
