package by.training.сonfectionery.model.service;

import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ProductService {

    void updateImage(Product product, InputStream inputStream) throws ServiceException;

    void createProduct(Map<String, String> productMap) throws ServiceException;

    List<Product> findProducts(int offset, int numberOfRecords, String[] productTypeId, int sortBy) throws ServiceException;
    List<Product> findProducts(int offset, int numberOfRecords) throws ServiceException;
    int numberOfRecords() throws ServiceException;
    int numberOfRecords(String[] productTypes) throws ServiceException;

}
