package by.training.сonfectionery.control.upload.impl;

import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.control.command.SessionAttribute;
import by.training.сonfectionery.control.upload.UploadCommand;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.ProductService;
import by.training.сonfectionery.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class UploadProductImageCommand implements UploadCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, InputStream inputStream) throws CommandException {
        ProductService productService = ProductServiceImpl.getInstance();
        Product product = (Product) request.getSession().getAttribute(SessionAttribute.PRODUCT);
        try {
            productService.updateImage(product, inputStream);
            return new Router(PagePath.GO_TO_EDIT_PRODUCT_PAGE, Router.RouteType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Failed to execute UploadProductPhotoCommand", e);
            throw new CommandException("Failed to execute UploadProductPhotoCommand", e);
        }
    }

}
