package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import by.training.сonfectionery.util.Base64Coder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static by.training.сonfectionery.control.command.RequestParameter.*;
import static by.training.сonfectionery.control.command.RequestAttribute.*;

public class RegistrationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String BASE_IMAGE_PATH = "/img/user.png";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);

        Map<String, String> userMap = new HashMap<>();
        userMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userMap.put(LAST_NAME, request.getParameter(LAST_NAME));
        userMap.put(EMAIL, request.getParameter(EMAIL));
        userMap.put(PASSWORD, request.getParameter(PASSWORD));

        UserService service = UserServiceImpl.getInstance();
        try {
            if (service.isEmailExist(userMap.get(EMAIL))) {
                request.setAttribute(REGISTRATION_USER_DATA, userMap);
                request.setAttribute(ERROR_EMAIL_EXISTS, userMap);
                return new Router(PagePath.REGISTRATION_PAGE, Router.RouteType.FORWARD);
            }

        } catch (ServiceException e) {
            logger.error("Executing registration command error", e);
            throw new CommandException("Executing registration command error", e);
        }

        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);

        if (service.validateUserData(userMap) && password.equals(passwordRepeat)) {
            try {
                userMap.put(IMAGE,loadBaseUserImage(request.getServletContext().getRealPath("") + BASE_IMAGE_PATH));
                service.registrate(userMap);
            } catch (ServiceException e) {
                logger.error("Executing registration command error", e);
                throw new CommandException("Executing registration command error", e);
            }
            return new Router(PagePath.CONFIRMATION_PAGE, Router.RouteType.REDIRECT);
        } else {
            request.setAttribute(REGISTRATION_USER_DATA, userMap);
            request.setAttribute(WRONG_REGISTRATION_DATA, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(WRONG_REGISTRATION_DATA));
            return new Router(PagePath.REGISTRATION_PAGE, Router.RouteType.FORWARD);
        }
    }

    private String loadBaseUserImage(String path) {
        String result = "";
        try (FileInputStream fis = new FileInputStream(path)) {
            result = Base64Coder.encode(fis);
        } catch (FileNotFoundException e) {
            logger.error("Can't find base user image file",e);
        } catch (IOException e) {
            logger.error("Can't load base user image file",e);
        }
        return result;
    }
}
