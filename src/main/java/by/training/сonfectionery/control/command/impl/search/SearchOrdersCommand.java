package by.training.сonfectionery.control.command.impl.search;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.dao.OrderDao;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.OrderDaoImpl;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import by.training.сonfectionery.model.service.OrderService;
import by.training.сonfectionery.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static by.training.сonfectionery.control.command.RequestAttribute.*;
import static by.training.сonfectionery.control.command.RequestParameter.*;

public class SearchOrdersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        OrderService orderService = OrderServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String[] orderStatuses = request.getParameterValues(ORDER_STATUS_ID);
        int page = 1;
        int sortBy = 1;
        if (request.getParameter(PAGE) != null)
            page = Integer.parseInt(request.getParameter(PAGE));
        try {
            Map<Order, Map<Product, Integer>> ordersMap;
            int numberOfRecords;
            if (orderStatuses == null) {
                ordersMap = orderService.findOrdersWithProducts(page, RECORDS_PER_PAGE);
                numberOfRecords = orderService.numberOfRecords();
            } else {
               ordersMap = orderService.findOrdersWithProducts(page, RECORDS_PER_PAGE, orderStatuses, sortBy);
               numberOfRecords = orderService.numberOfRecords(orderStatuses);
            }
            if (numberOfRecords == 0){
                request.setAttribute(ERROR_NO_ORDERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_ORDERS_FOUND));
                return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);
            }
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(ORDERS_MAP, ordersMap);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);
        } catch (ServiceException e) {
            //logger.error("Failed to execute GetOrders command", e);
            throw new CommandException("Failed to execute SearchOrdersCommand", e);
        }

    }
}
