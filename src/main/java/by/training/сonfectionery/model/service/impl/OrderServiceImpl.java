/*
package by.training.сonfectionery.model.service.impl;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import by.training.сonfectionery.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.training.сonfectionery.control.command.RequestParameter.*;
import static by.training.сonfectionery.control.command.RequestParameter.NUMBER_IN_STOCK;

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
    public void createOrder(Map<String, String> orderMap) throws ServiceException {
        Order order = new Order.OrderBuilder()
                .setDate(orderMap.get(DATE))
                .setPhone(orderMap.get(PHONE))
                .setUserID(orderMap.get(USER_ID))
                .setOrderStatus(Integer.parseInt(productMap.get(WEIGHT)))
                .createProduct();

        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(productDao);
            productDao.create(product);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in createProduct method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in createProduct  method", e);
            }
        }
    }
}
*/
