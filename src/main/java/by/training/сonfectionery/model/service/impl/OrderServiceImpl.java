
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
import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
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
    public Map<Order, Map<Product, Integer>> findOrders(int page, int numberOfRecords) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.initTransaction(orderDao, productDao);
            List<Order> orders = orderDao.findAll((page - 1) * 12, 12);
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
        } catch (DaoException e){
            throw new ServiceException("Failed to make transaction in acceptOrder method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e){
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
        } catch (DaoException e){
            throw new ServiceException("Failed to make transaction in doneOrder method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e){
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
        } catch (DaoException e){
            throw new ServiceException("Failed to make transaction in rejectOrder method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e){
                logger.error("Can't end transaction in rejectOrder method", e);
            }
        }
    }

    @Override
    public int numberOfRecords() throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.init(orderDao);
            return orderDao.getNumberOfRecords();
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in numberOfRecords method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in numberOfRecords  method", e);
            }
        }
    }
}

