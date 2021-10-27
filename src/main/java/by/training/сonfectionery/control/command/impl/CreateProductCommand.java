package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.ProductService;
import by.training.сonfectionery.model.service.impl.ProductServiceImpl;
import by.training.сonfectionery.util.Base64Coder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static by.training.сonfectionery.control.command.RequestParameter.*;
import static by.training.сonfectionery.control.command.RequestAttribute.*;

public class CreateProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String BASE_IMAGE_PATH = "/img/user.png";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        Map<String, String> productMap = new HashMap<>();
        productMap.put(NAME, request.getParameter(NAME));
        productMap.put(PRICE, request.getParameter(PRICE));
        productMap.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        productMap.put(WEIGHT, request.getParameter(WEIGHT));
        productMap.put(PRODUCT_TYPE_ID, request.getParameter(PRODUCT_TYPE_ID));
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            if (productService.validateProductData(productMap)) {
                productMap.put(IMAGE, loadBaseUserImage(request.getServletContext().getRealPath("") + BASE_IMAGE_PATH));
                productService.createProduct(productMap);
                return new Router(PagePath.GO_TO_CREATE_PRODUCT_PAGE, Router.RouteType.REDIRECT);
            } else {
                request.setAttribute(CREATE_PRODUCT_DATA, productMap);
                request.setAttribute(WRONG_CREATE_PRODUCT_DATA, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(WRONG_CREATE_PRODUCT_DATA));
                return new Router(PagePath.CREATE_PRODUCT_PAGE, Router.RouteType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Failed to execute CreateProductCommand", e);
            throw new CommandException("Failed to execute CreateProductCommand", e);
        }
    }

    private String loadBaseUserImage(String path) {
        String result = "";
        try (FileInputStream fis = new FileInputStream(path)) {
            result = Base64Coder.encode(fis);
        } catch (FileNotFoundException e) {
            logger.error("Can't find base user image file", e);
        } catch (IOException e) {
            logger.error("Can't load base user image file", e);
        }
        return result;
    }
}
