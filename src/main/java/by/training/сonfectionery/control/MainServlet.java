package by.training.сonfectionery.control;

import java.io.*;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.CommandFactory;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "mainServlet", urlPatterns = "/controller")
public class MainServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getInstance().createCommand(request);
        Router router = null;
        try {
            router = command.execute(request);
        } catch (CommandException e) {
            //router = new Router(PagePath.ERROR_404_PAGE, Router.RouteType.REDIRECT); // FIXME: 18.09.2021
            e.printStackTrace();
        }
        switch (router.getRouteType()) {
            case FORWARD:
                request.getRequestDispatcher(router.getPagePath()).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + router.getPagePath());
                break;
        }
    }

    public void destroy() {
    }
}