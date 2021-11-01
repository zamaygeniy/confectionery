package by.training.сonfectionery.control.command.impl.search;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.OrderService;
import by.training.сonfectionery.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static by.training.сonfectionery.control.command.RequestAttribute.*;
import static by.training.сonfectionery.control.command.RequestAttribute.CURRENT_PAGE;
import static by.training.сonfectionery.control.command.RequestParameter.ORDER_STATUS_ID;

public class SearchUserOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        OrderService orderService = OrderServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String[] orderStatuses = request.getParameterValues(ORDER_STATUS_ID);
        List<Integer> orderStatusesList = new ArrayList<>();

        if (orderStatuses != null) {
            for (String orderStatus : orderStatuses) {
                try {
                    orderStatusesList.add(Integer.parseInt(orderStatus));
                } catch (NumberFormatException e) {
                    request.setAttribute(ERROR_NO_ORDERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_ORDERS_FOUND));
                    return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);
                }
            }
        }


        User user = (User) session.getAttribute(SessionAttribute.USER);
        int page = 1;
        if (request.getParameter(PAGE) != null) {
            try {
                page = Integer.parseInt(request.getParameter(PAGE));
            } catch (NumberFormatException e) {
                request.setAttribute(ERROR_NO_ORDERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_ORDERS_FOUND));
                return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);
            }
        }

        try {
            Map<Order, Map<Product, Integer>> ordersMap;
            int numberOfRecords;
            if (orderStatuses == null) {
                ordersMap = orderService.findOrdersWithProducts((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, user.getId());
                numberOfRecords = orderService.numberOfRecords(user.getId());
            } else {
                ordersMap = orderService.findOrdersWithProducts((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, user.getId(), orderStatusesList);
                numberOfRecords = orderService.numberOfRecords(user.getId(), orderStatusesList);
            }
            if (numberOfRecords == 0) {
                request.setAttribute(ERROR_NO_ORDERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_ORDERS_FOUND));
                return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);
            }
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(ORDERS_MAP, ordersMap);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Failed to execute SearchUserOrdersCommand", e);
            throw new CommandException("Failed to execute SearchUserOrdersCommand", e);
        }

    }
}
