package by.training.сonfectionery.control.command.impl.search;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static by.training.сonfectionery.control.command.RequestAttribute.*;
import static by.training.сonfectionery.control.command.RequestParameter.*;

public class SearchUsersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String email = request.getParameter(EMAIL);
        String[] userStatuses = request.getParameterValues(STATUS_ID);
        int page = 1;

        if (request.getParameter(PAGE) != null)
            page = Integer.parseInt(request.getParameter(PAGE));
        try {
            List<User> userList = new ArrayList<>();
            int numberOfRecords;
            if (email != null) {
                Optional<User> user = userService.findUserByEmail(email);
                if (user.isPresent()) {
                    userList.add(user.get());
                    request.setAttribute(USER_LIST, userList);
                } else {
                    request.setAttribute(ERROR_NO_USERS_FOUND, MessageManager.valueOf(locale.toUpperCase(Locale.ROOT)).getMessage(ERROR_NO_USERS_FOUND));
                }
                return new Router(PagePath.USERS_PAGE, Router.RouteType.FORWARD);

            }
            if (userStatuses == null) {

                userList = userService.findUsers(page, RECORDS_PER_PAGE);
                numberOfRecords = userService.numberOfRecords();
            } else {
                for (int i = 0; i < userStatuses.length; i++)
                    System.out.println(userStatuses[i]);
                userList = userService.findUsersByStatusId(page, RECORDS_PER_PAGE, userStatuses);
                numberOfRecords = userService.numberOfRecords(userStatuses);
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
        } catch (ServiceException e) {
            //logger.error("Failed to execute GetOrders command", e);
            throw new CommandException("Failed to execute SearchUsersCommand", e);
        }

    }
}