package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = request.getParameter(RequestParameter.LOCALE);
        String currentPage= (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        session.setAttribute(SessionAttribute.LOCALE, locale);
        return new Router(currentPage, Router.RouteType.FORWARD);
    }
}
