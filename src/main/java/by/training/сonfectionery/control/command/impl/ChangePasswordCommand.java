package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import by.training.сonfectionery.model.validator.UserValidator;
import by.training.сonfectionery.model.validator.impl.UserValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

import static by.training.сonfectionery.control.command.RequestAttribute.*;
import static by.training.сonfectionery.control.command.RequestAttribute.ERROR_WRONG_DATA;
import static by.training.сonfectionery.control.command.RequestParameter.*;
import static by.training.сonfectionery.control.command.RequestParameter.NEW_PASSWORD;

public class ChangePasswordCommand implements Command{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String password = request.getParameter(PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);

        UserService userService = UserServiceImpl.getInstance();
        User user = (User) session.getAttribute(SessionAttribute.USER);

        try {
            if (userService.checkPassword(user, password)) {
                userService.updatePassword(user, newPassword);
                request.getSession().setAttribute(SUCCESSFUL_PASSWORD_EDIT, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(SUCCESSFUL_PASSWORD_EDIT));
            } else {
                request.setAttribute(RequestAttribute.ERROR_WRONG_PASSWORD, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_WRONG_PASSWORD));
                return new Router(PagePath.PROFILE_PAGE, Router.RouteType.FORWARD);
            }
            return new Router(PagePath.GO_TO_PROFILE_PAGE, Router.RouteType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Failed to change password", e);
            throw new CommandException("Failed to change password", e);
        }
    }
}
