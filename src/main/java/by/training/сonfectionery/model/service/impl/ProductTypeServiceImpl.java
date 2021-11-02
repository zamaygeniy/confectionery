package by.training.сonfectionery.model.service.impl;

import by.training.сonfectionery.model.domain.ProductType;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.dao.ProductTypeDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.ProductTypeDaoImpl;
import by.training.сonfectionery.model.service.ProductTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductTypeServiceImpl implements ProductTypeService {

    private static final Logger logger = LogManager.getLogger();

    private static final ProductTypeServiceImpl instance = new ProductTypeServiceImpl();

    private ProductTypeServiceImpl() {
    }

    public static ProductTypeServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<ProductType> findAllProductTypes() throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        ProductTypeDao productTypeDao = new ProductTypeDaoImpl();
        try {
            transaction.init(productTypeDao);
            return productTypeDao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to find all product types in findAllProductTypes method", e);
            throw new ServiceException("Failed to find all product types in findAllProductTypes method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                throw new ServiceException("Failed to end transaction in findAllProductTypes method", e);
            }
        }
    }
}
