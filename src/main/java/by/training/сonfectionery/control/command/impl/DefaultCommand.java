package by.training.сonfectionery.control.command.impl;
import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.GO_TO_MAIN_PAGE, Router.RouteType.REDIRECT);
    }
}
