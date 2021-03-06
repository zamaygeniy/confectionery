package by.training.сonfectionery.control.command.impl.admin;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.RequestParameter;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.OrderService;
import by.training.сonfectionery.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AcceptOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String orderId = request.getParameter(RequestParameter.ORDER_ID);
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            orderService.acceptOrder(Integer.parseInt(orderId));
        } catch (ServiceException e) {
            logger.error("Failed to execute AcceptOrderCommand", e);
            throw new CommandException("Failed to execute AcceptOrderCommand", e);
        }
        return new Router(PagePath.GO_TO_ORDERS_PAGE, Router.RouteType.REDIRECT);
    }
}
