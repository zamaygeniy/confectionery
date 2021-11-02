package by.training.сonfectionery.control.command.impl.search;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.model.domain.User;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static by.training.сonfectionery.control.command.RequestAttribute.*;
import static by.training.сonfectionery.control.command.RequestParameter.*;

public class SearchUsersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String[] userStatuses = request.getParameterValues(STATUS_ID);
        List<Integer> userStatusesList = new ArrayList<>();
        String id = request.getParameter(ID);
        if (id == null || id.equals("")){
            id = null;
        }
        if (userStatuses != null) {
            for (String userStatus : userStatuses) {
                try {
                    userStatusesList.add(Integer.parseInt(userStatus));
                } catch (NumberFormatException e) {
                    request.setAttribute(ERROR_NO_USERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_USERS_FOUND));
                    return new Router(PagePath.USERS_PAGE, Router.RouteType.FORWARD);
                }
            }
        }

        int page = 1;
        if (request.getParameter(PAGE) != null) {
            try {
                page = Integer.parseInt(request.getParameter(PAGE));
            } catch (NumberFormatException e) {
                request.setAttribute(ERROR_NO_USERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_USERS_FOUND));
                return new Router(PagePath.USERS_PAGE, Router.RouteType.FORWARD);
            }
        }

        try {
            List<User> userList = new ArrayList<>();
            if (id != null && userStatuses == null) {
                Optional<User> user = userService.findUserById(Integer.parseInt(id));
                if (user.isPresent()) {
                    userList.add(user.get());
                    request.setAttribute(USER_LIST, userList);
                } else {
                    request.setAttribute(ERROR_NO_USERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_USERS_FOUND));
                }
                return new Router(PagePath.USERS_PAGE, Router.RouteType.FORWARD);
            }

            int numberOfRecords;
            if (userStatuses == null  && id == null) {
                userList = userService.findUsers((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                numberOfRecords = userService.numberOfRecords();
            } else {
                userList = userService.findUsersByStatusId((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, userStatusesList);
                numberOfRecords = userService.numberOfRecords(userStatusesList);
            }
            if (numberOfRecords == 0) {
                request.setAttribute(ERROR_NO_USERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_USERS_FOUND));
                return new Router(PagePath.USERS_PAGE, Router.RouteType.FORWARD);
            }
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(USER_LIST, userList);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            return new Router(PagePath.USERS_PAGE, Router.RouteType.FORWARD);
        } catch (NumberFormatException e) {
            request.setAttribute(ERROR_NO_USERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_USERS_FOUND));
            return new Router(PagePath.USERS_PAGE, Router.RouteType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Failed to execute SearchUsersCommand", e);
            throw new CommandException("Failed to execute SearchUsersCommand", e);
        }

    }
}