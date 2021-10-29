package by.training.сonfectionery.control.command;

import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;

public interface UploadCommand {
    Router execute(HttpServletRequest request, InputStream inputStream) throws CommandException;
}
