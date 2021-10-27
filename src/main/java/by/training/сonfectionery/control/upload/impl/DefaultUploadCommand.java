package by.training.сonfectionery.control.upload.impl;

import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.control.upload.UploadCommand;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;

public class DefaultUploadCommand implements UploadCommand {
    @Override
    public Router execute(HttpServletRequest request, InputStream inputStream) throws CommandException {
        return new Router(PagePath.GO_TO_MAIN_PAGE, Router.RouteType.REDIRECT);
    }
}
