package by.training.сonfectionery.model.service.impl;

import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import by.training.сonfectionery.model.service.ProductService;
import by.training.сonfectionery.model.validator.impl.ProductValidatorImpl;
import by.training.сonfectionery.model.validator.impl.UserValidatorImpl;
import by.training.сonfectionery.util.Base64Coder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.training.сonfectionery.control.command.RequestParameter.*;

public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger();

    private static ProductServiceImpl instance;

    private ProductServiceImpl() {
    }

    public static ProductServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProductServiceImpl();
        }
        return instance;
    }


    @Override
    public boolean validateProductData(Map<String, String> productMap) throws ServiceException {
        return ProductValidatorImpl.getInstance().validateProductData(productMap);
    }

    @Override
    public void updateImage(Product product, InputStream inputStream) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        ProductDao productDao = new ProductDaoImpl();
        product.setImage(Base64Coder.encode(inputStream));
        try {
            transaction.init(productDao);
            productDao.update(product);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in updateImage method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in updateImage method", e);
            }
        }
    }

    @Override
    public void createProduct(Map<String, String> productMap) throws ServiceException {
        Product product = new Product.ProductBuilder()
                .setName(productMap.get(NAME))
                .setPrice(Double.parseDouble(productMap.get(PRICE)))
                .setDescription(productMap.get(DESCRIPTION))
                .setWeight(Integer.parseInt(productMap.get(WEIGHT)))
                .setImage(productMap.get(IMAGE))
                .setProductTypeId(Integer.parseInt(productMap.get(PRODUCT_TYPE_ID)))
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

    @Override
    public List<Product> findProducts(int offset, int numberOfRecords, String[] productTypeId, int sortBy) throws ServiceException {

        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        List<Product> products;
        try {
            transaction.init(productDao);
            products = productDao.findProductByProductTypeId(offset, numberOfRecords,productTypeId, sortBy);
            return products;
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in findProductsByProductType method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in findProductsByProductType  method", e);
            }
        }
    }


    @Override
    public List<Product> findProducts(int offset, int numberOfRecords) throws ServiceException {
        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        List<Product> products;
        try {
            transaction.init(productDao);
            products = productDao.findAll(offset, numberOfRecords);
            return products;
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in findProductsByProductType method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in findProductsByProductType  method", e);
            }
        }
    }


    @Override
    public int numberOfRecords() throws ServiceException {
        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(productDao);
            return productDao.getNumberOfRecords();
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in numberOfRecords method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in numberOfRecords method", e);
            }
        }
    }

    @Override
    public int numberOfRecords(String[] productTypes) throws ServiceException {
        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(productDao);
            return productDao.getNumberOfRecords(productTypes);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in numberOfRecords with product types method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in numberOfRecords product types method", e);
            }
        }
    }
}
