package by.training.сonfectionery.control.command.impl.admin;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.RequestParameter;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MakeUserAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(RequestParameter.ID);
        UserService userService = UserServiceImpl.getInstance();
        try {
            userService.makeUserAdmin(Integer.parseInt(id));
        } catch (ServiceException e) {
            logger.error("Failed to execute MakeUserAdminCommand", e);
            throw new CommandException("Failed to execute MakeUserAdminCommand", e);
        }
        return new Router(PagePath.GO_TO_USERS_PAGE, Router.RouteType.REDIRECT);
    }
}
