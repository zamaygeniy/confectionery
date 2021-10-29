package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.control.command.SessionAttribute;
import by.training.сonfectionery.domain.User;
import static by.training.сonfectionery.control.command.RequestParameter.*;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.OrderService;
import by.training.сonfectionery.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        User user = (User) request.getSession().getAttribute(SessionAttribute.USER);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(request.getParameter(CART));
        Map<Integer, Integer> productsMap = new HashMap<>();
        while (matcher.find()){
            int id = Integer.parseInt(matcher.group());
            matcher.find();
            productsMap.put(id, Integer.parseInt(matcher.group()));
        }
        Map<String, String> orderMap = new HashMap<>();
        orderMap.put(PHONE, request.getParameter(PHONE));
        orderMap.put(USER_ID, String.valueOf(user.getId()));
        orderMap.put(COST, request.getParameter(COST));
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            orderService.createOrder(orderMap, productsMap);
        } catch (ServiceException e) {
            throw new CommandException("Failed to create order", e);
        }
        return new Router(PagePath.GO_TO_CART_PAGE, Router.RouteType.REDIRECT);
    }
}
