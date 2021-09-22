package by.training.сonfectionery.model.service;

import by.training.сonfectionery.exception.ServiceException;

import java.util.Map;

public interface OrderService {

    void createOrder(Map<String, String> orderMap) throws ServiceException;

}
