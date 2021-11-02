package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import by.training.сonfectionery.model.util.Base64Coder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static by.training.сonfectionery.control.command.RequestParameter.*;
import static by.training.сonfectionery.control.command.RequestAttribute.*;

public class RegistrationCommand implements UploadCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, InputStream image) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        Map<String, String> userMap = new HashMap<>();
        userMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userMap.put(LAST_NAME, request.getParameter(LAST_NAME));
        userMap.put(EMAIL, request.getParameter(EMAIL));
        userMap.put(PASSWORD, request.getParameter(PASSWORD));
        String encodedImage = null;
        if (image != null){
            encodedImage = Base64Coder.encode(image);
        }
        userMap.put(IMAGE, encodedImage);

        UserService service = UserServiceImpl.getInstance();
        try {
            if (service.isEmailExist(userMap.get(EMAIL))) {
                request.setAttribute(ERROR_EMAIL_EXISTS, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_EMAIL_EXISTS));
                return new Router(PagePath.REGISTRATION_PAGE, Router.RouteType.FORWARD);
            }

        } catch (ServiceException e) {
            logger.error("Failed to execute RegistrationCommand", e);
            throw new CommandException("Failed to execute RegistrationCommand", e);
        }

        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);

        if (service.validateUserData(userMap) && password.equals(passwordRepeat)) {
            try {
                service.register(userMap);
            } catch (ServiceException e) {
                logger.error("Failed to execute RegistrationCommand", e);
                throw new CommandException("Failed to execute RegistrationCommand", e);
            }
            return new Router(PagePath.GO_TO_CONFIRMATION_PAGE, Router.RouteType.REDIRECT);
        } else {
            request.setAttribute(REGISTRATION_USER_DATA, userMap);
            request.setAttribute(WRONG_REGISTRATION_DATA, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(WRONG_REGISTRATION_DATA));
            return new Router(PagePath.REGISTRATION_PAGE, Router.RouteType.FORWARD);
        }
    }

}
