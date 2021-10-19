package by.training.сonfectionery.control.filter;

import by.training.сonfectionery.control.command.CommandType;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.RequestParameter;
import by.training.сonfectionery.control.command.SessionAttribute;
import by.training.сonfectionery.domain.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = "/controller")
public class RoleFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("RoleFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionAttribute.USER) == null) {
            User user = new User.UserBuilder()
                    .setRole(User.Role.GUEST)
                    .createUser();
            session.setAttribute(SessionAttribute.USER, user);
        }
        String commandName = request.getParameter(RequestParameter.COMMAND);
        CommandType commandType;
        if (commandName != null) {
            try {
                commandType = CommandType.valueOf(commandName.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                logger.error("Invalid command name", e);
                commandType = CommandType.DEFAULT;
            }
            if (commandType != CommandType.CHANGE_LOCALE) {
                if (request.getQueryString() != null) {
                    session.setAttribute(SessionAttribute.CURRENT_URL, "/controller?" + request.getQueryString());
                    System.out.println(session.getAttribute(SessionAttribute.CURRENT_URL));
                }
            }

        } else {
            commandType = CommandType.DEFAULT;
        }
        RoleCommandProvider roleCommandProvider = RoleCommandProvider.getInstance();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (!roleCommandProvider.checkCommand(user.getRole(), commandType)) {
            response.sendRedirect(request.getContextPath() + PagePath.GO_TO_MAIN_PAGE);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}
