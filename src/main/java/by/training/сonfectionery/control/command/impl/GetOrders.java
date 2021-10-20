/*
package by.training.сonfectionery.control.command.impl;

import static by.training.сonfectionery.control.command.RequestAttribute.*;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.OrderService;
import by.training.сonfectionery.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class GetOrders implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        OrderService orderService = OrderServiceImpl.getInstance();
        int page = 1;
        if (request.getParameter(PAGE) != null)
            page = Integer.parseInt(request.getParameter(PAGE));
        try {
            Map<Order, Map<Product, Integer>> ordersMap = orderService.findOrders(page, RECORDS_PER_PAGE);
            int numberOfRecords = orderService.numberOfRecords();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(ORDERS_MAP, ordersMap);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
        } catch (ServiceException e) {
            logger.error("Failed to execute GetOrders command", e);
            throw new CommandException("Failed to execute GetOrders command", e);
        }
        return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);

    }
}
*/
