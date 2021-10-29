package by.training.сonfectionery.control.command;

import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;

public interface Command {

    Router execute(HttpServletRequest request) throws CommandException;
}
