package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class VerificationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService service = UserServiceImpl.getInstance();
        String id = request.getParameter(RequestParameter.ID);
        try {
            if (service.verify(id)) {
                return new Router(PagePath.GO_TO_LOGIN_PAGE, Router.RouteType.REDIRECT);
            }
        } catch (ServiceException e) {
            throw new CommandException("Executing verify command error", e);
        }
        return new Router(PagePath.MAIN_PAGE, Router.RouteType.FORWARD);

    }
}
