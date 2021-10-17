
package by.training.сonfectionery.model.service.impl;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.dao.OrderDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.OrderDaoImpl;
import by.training.сonfectionery.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;

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
}

