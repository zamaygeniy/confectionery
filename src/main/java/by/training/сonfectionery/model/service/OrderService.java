package by.training.сonfectionery.model.service;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.ServiceException;

import java.util.Map;

public interface OrderService {

    void createOrder(Map<String, String> orderMap, Map<Integer, Integer> productsMap) throws ServiceException;

    Map<Order, Map<Product, Integer>> findOrders(int offset, int numberOfRecords) throws ServiceException;
    void doneOrder(int orderId) throws ServiceException;
    void acceptOrder(int orderId) throws ServiceException;
    void rejectOrder(int orderId) throws ServiceException;
    int numberOfRecords() throws ServiceException;

}
