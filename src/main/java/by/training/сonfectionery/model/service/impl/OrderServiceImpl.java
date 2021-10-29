
package by.training.сonfectionery.model.service.impl;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.dao.OrderDao;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.OrderDaoImpl;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import by.training.сonfectionery.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.training.сonfectionery.control.command.RequestParameter.*;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();

    private static OrderServiceImpl instance;

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public void createOrder(Map<String, String> orderMap, Map<Integer, Integer> productsMap) throws ServiceException {
        Order order = new Order.OrderBuilder()
                .setDate(LocalDate.now())
                .setPhone(orderMap.get(PHONE))
                .setCost(Double.parseDouble(orderMap.get(COST)))
                .setStatus(Order.Status.WAITING_FOR_CONFIRMATION)
                .setUserId(Integer.parseInt(orderMap.get(USER_ID)))
                .createOrder();
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.init(orderDao);
            orderDao.create(order);
            for (Map.Entry<Integer, Integer> product : productsMap.entrySet()) {
                orderDao.addProducts(order.getId(), product.getKey(), product.getValue());
            }

        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in createOrder method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in createOrder  method", e);
            }
        }
    }

    @Override
    public Map<Order, Map<Product, Integer>> findOrdersWithProducts(int offset, int recordsPerPage) throws ServiceException {
        return findOrdersWithProducts(offset, recordsPerPage, -1, null);
    }

    @Override
    public Map<Order, Map<Product, Integer>> findOrdersWithProducts(int offset, int recordsPerPage, int userId) throws ServiceException {
        return findOrdersWithProducts(offset, recordsPerPage, userId, null);
    }

    @Override
    public Map<Order, Map<Product, Integer>> findOrdersWithProducts(int offset, int recordsPerPage, List<Integer> statusId) throws ServiceException {
        return findOrdersWithProducts(offset, recordsPerPage, -1, statusId);
    }

    @Override
    public Map<Order, Map<Product, Integer>> findOrdersWithProducts(int offset, int recordsPerPage, int userId, List<Integer> statusId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.initTransaction(orderDao, productDao);
            List<Order> orders;
            if (userId > 0 && statusId != null) {
                orders = orderDao.findOrders(offset, recordsPerPage, userId, statusId);
            } else if (userId > 0) {
                orders = orderDao.findOrders(offset, recordsPerPage, userId);
            } else if (statusId != null) {
                orders = orderDao.findOrders(offset, recordsPerPage, statusId);
            } else {
                orders = orderDao.findOrders(offset, recordsPerPage);
            }
            Map<Order, Map<Product, Integer>> ordersMap = new HashMap<>();
            for (Order order : orders) {
                Map<Integer, Integer> productsInOrder;
                productsInOrder = orderDao.findProductsInOrders(order.getId());
                Map<Product, Integer> productsAmount = new HashMap<>();
                for (Map.Entry<Integer, Integer> product : productsInOrder.entrySet()) {
                    Optional<Product> optionalProduct = productDao.findById(product.getKey());
                    if (optionalProduct.isPresent()) {
                        productsAmount.put(optionalProduct.get(), product.getValue());
                    } else {
                        throw new ServiceException("There is no such product");
                    }
                }
                ordersMap.put(order, productsAmount);
            }
            return ordersMap;
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in findOrders method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in findOrders  method", e);
            }
        }
    }

    @Override
    public void acceptOrder(int orderId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.init(orderDao);
            orderDao.updateOrderStatus(orderId, Order.Status.IN_PROCESS);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in acceptOrder method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in acceptOrder method", e);
            }
        }
    }

    @Override
    public void doneOrder(int orderId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.init(orderDao);
            orderDao.updateOrderStatus(orderId, Order.Status.DONE);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in doneOrder method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in doneOrder method", e);
            }
        }
    }


    @Override
    public void rejectOrder(int orderId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.init(orderDao);
            orderDao.updateOrderStatus(orderId, Order.Status.CANCELLED);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in rejectOrder method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in rejectOrder method", e);
            }
        }
    }

    @Override
    public int numberOfRecords() throws ServiceException {
        return numberOfRecords(-1, null);
    }

    @Override
    public int numberOfRecords(List<Integer> statusId) throws ServiceException {
        return numberOfRecords(-1, statusId);
    }

    @Override
    public int numberOfRecords(int userId) throws ServiceException {
        return numberOfRecords(userId, null);
    }

    @Override
    public int numberOfRecords(int userId, List<Integer> statusId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.init(orderDao);
            if (userId > 0 && statusId != null) {
                return orderDao.getNumberOfRecords(userId, statusId);
            } else if (statusId == null) {
                return orderDao.getNumberOfRecords(userId);
            } else if (userId < 0) {
                return orderDao.getNumberOfRecords(statusId);
            } else {
                return orderDao.getNumberOfRecords();
            }

        } catch (
                DaoException e) {
            throw new ServiceException("Failed to make transaction in numberOfRecords method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in numberOfRecords method", e);
            }
        }
    }


}

