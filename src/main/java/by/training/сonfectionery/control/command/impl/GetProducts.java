package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.domain.Order;
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
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        ProductDao dao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        int recordsPerPage = 12;
        try {
            transaction.init(dao);
            List<Product> list = dao.findAll((page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecords = dao.getNumberOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("productList", list);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            return new Router(PagePath.CATALOG_PAGE, Router.RouteType.FORWARD);
        } catch (DaoException e) {
            throw new CommandException("Failed", e);
        } finally {

            try {
                transaction.end();
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }
}
