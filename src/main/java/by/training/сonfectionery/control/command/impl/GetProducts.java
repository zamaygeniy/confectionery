package by.training.сonfectionery.control.command.impl;

import static by.training.сonfectionery.control.command.RequestAttribute.*;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetProducts implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        int page = 1;
        if (request.getParameter(PAGE) != null)
            page = Integer.parseInt(request.getParameter(PAGE));
        ProductDao dao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(dao);
            List<Product> list = dao.findAll((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            int numberOfRecords = dao.getNumberOfRecords();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
        request.setAttribute(PRODUCT_LIST, list);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
        } catch (DaoException e) {
            throw new CommandException("Failed to execute GetProducts command", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }
}
