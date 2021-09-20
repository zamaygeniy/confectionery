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
        String userId = request.getParameter(RequestParameter.ID);
        try {
            service.verify(userId);
        } catch (ServiceException e) {
            throw new CommandException("Executing verify command error", e);
        }
        return new Router(PagePath.MAIN_PAGE, Router.RouteType.REDIRECT);

    }
}
