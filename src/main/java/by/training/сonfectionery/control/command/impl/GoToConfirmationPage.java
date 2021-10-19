package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToConfirmationPage implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(PagePath.CONFIRMATION_PAGE, Router.RouteType.FORWARD);
    }
}