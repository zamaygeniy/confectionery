package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Locale;

import static by.training.сonfectionery.control.command.RequestAttribute.*;
import static by.training.сonfectionery.control.command.RequestParameter.PRODUCT_TYPE_ID;

public class SearchOrdersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String[] productTypes = request.getParameterValues(PRODUCT_TYPE_ID);
        int page = 1;
        if (request.getParameter(PAGE) != null)
            page = Integer.parseInt(request.getParameter(PAGE));
        ProductDao dao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(dao);
            int numberOfRecords = dao.getNumberOfRecordsWithProductTypeId(productTypes);
            if (numberOfRecords == 0){
                request.setAttribute(ERROR_NO_PRODUCTS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_PRODUCTS_FOUND));
                return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
            }
            List<Product> list = dao.findProductByProductTypeId(productTypes,(page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, 1);
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(PRODUCT_LIST, list);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
        } catch (DaoException e) {
            throw new CommandException("Failed to execute SearchProductsCommand", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }

    }
    }
}
