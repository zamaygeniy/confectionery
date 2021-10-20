package by.training.сonfectionery.model.service;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void createOrder(Map<String, String> orderMap, Map<Integer, Integer> productsMap) throws ServiceException;
    Map<Order, Map<Product, Integer>> findOrdersWithProducts(int offset, int numberOfRecords) throws ServiceException;
    Map<Order, Map<Product, Integer>> findOrdersWithProducts(int offset, int numberOfRecords, String[] statusId, int sortBy) throws ServiceException;
    void doneOrder(int orderId) throws ServiceException;
    void acceptOrder(int orderId) throws ServiceException;
    void rejectOrder(int orderId) throws ServiceException;
    int numberOfRecords() throws ServiceException;
    int numberOfRecords(String[] statusId) throws ServiceException;

}
