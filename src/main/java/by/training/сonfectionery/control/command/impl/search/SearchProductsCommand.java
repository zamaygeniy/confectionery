package by.training.сonfectionery.control.command.impl.search;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.ProductService;
import by.training.сonfectionery.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import static by.training.сonfectionery.control.command.RequestAttribute.*;
import static by.training.сonfectionery.control.command.RequestParameter.*;

public class SearchProductsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ProductService productService = ProductServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String[] productTypes = request.getParameterValues(PRODUCT_TYPE_ID);
        List<Integer> productTypesList = new ArrayList<>();
        if (productTypes != null){
            for (String productType : productTypes) {
                try {
                    productTypesList.add(Integer.parseInt(productType));
                } catch (NumberFormatException e) {
                    request.setAttribute(ERROR_NO_PRODUCTS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_PRODUCTS_FOUND));
                    return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
                }
            }
        }

        int page = 1;
        int sortBy = 1;
        if (request.getParameter(PAGE) != null) {
            try {
                page = Integer.parseInt(request.getParameter(PAGE));
            } catch (NumberFormatException e) {
                request.setAttribute(ERROR_NO_PRODUCTS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_PRODUCTS_FOUND));
                return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
            }
        }

        try {
            List<Product> productList;
            int numberOfRecords;
            if (productTypes == null) {
                productList = productService.findProducts(page - 1, RECORDS_PER_PAGE);
                numberOfRecords = productService.numberOfRecords();
            } else {
                productList = productService.findProducts(page - 1, RECORDS_PER_PAGE, productTypesList, sortBy);
                numberOfRecords = productService.numberOfRecords(productTypesList);
            }
            if (numberOfRecords == 0) {
                request.setAttribute(ERROR_NO_PRODUCTS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_PRODUCTS_FOUND));
                return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
            }
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(PRODUCT_LIST, productList);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
        } catch (ServiceException e) {
            //logger.error("Failed to execute GetOrders command", e);
            throw new CommandException("Failed to execute SearchOrdersCommand", e);
        }
    }
}
