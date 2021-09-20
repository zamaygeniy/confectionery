package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.control.command.SessionAttribute;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.USER);
        session.removeAttribute(SessionAttribute.USER_IMAGE);
        return new Router(PagePath.MAIN_PAGE, Router.RouteType.REDIRECT);
    }
}
