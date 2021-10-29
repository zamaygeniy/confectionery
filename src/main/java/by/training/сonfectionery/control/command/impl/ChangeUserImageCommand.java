package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.control.command.SessionAttribute;
import by.training.сonfectionery.control.command.UploadCommand;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class ChangeUserImageCommand implements UploadCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, InputStream inputStream) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        User user = (User) request.getSession().getAttribute(SessionAttribute.USER);
        try {
            if (inputStream != null){
                userService.updateImage(user, inputStream);
            }
            return new Router(PagePath.GO_TO_PROFILE_PAGE, Router.RouteType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Executing changeUserImage command error", e);
            throw new CommandException("Executing changeUserImage command error", e);
        }
    }

}
