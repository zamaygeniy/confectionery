package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class EditProfileCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
