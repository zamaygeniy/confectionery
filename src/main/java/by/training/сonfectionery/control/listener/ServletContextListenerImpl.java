package by.training.сonfectionery.control.listener;

import by.training.сonfectionery.domain.ProductType;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.connection.ConnectionPool;
import by.training.сonfectionery.model.service.ProductTypeService;
import by.training.сonfectionery.model.service.impl.ProductTypeServiceImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ConnectionPool.getInstance();
        ProductTypeService productTypeService = ProductTypeServiceImpl.getInstance();
        final String PRODUCT_TYPES = "product_types";
        try {
            List<ProductType> productTypeList = productTypeService.findAllProductTypes();
            event.getServletContext().setAttribute(PRODUCT_TYPES, productTypeList);
        } catch (ServiceException e) {
            logger.error("Can't find product types while context initialized", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}