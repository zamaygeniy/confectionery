package by.training.сonfectionery.control;

import java.io.*;

import by.training.сonfectionery.control.command.*;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "mainServlet", urlPatterns = "/controller")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
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
        try {
            Router router;
            try {
                InputStream image = null;
                Part part = request.getPart(RequestParameter.FILE);
                if (part.getSize() != 0){
                    image = part.getInputStream();
                }
                UploadCommand uploadCommand = UploadCommandFactory.getInstance().createCommand(request);
                router = uploadCommand.execute(request, image);
            } catch (ServletException e){
                Command command = CommandFactory.getInstance().createCommand(request);
                router = command.execute(request);
            }
            switch (router.getRouteType()) {
                case FORWARD -> request.getRequestDispatcher(router.getPagePath()).forward(request, response);
                case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPagePath());
                default -> {
                    logger.error("Incorrect route type " + router.getRouteType());
                    response.sendRedirect(request.getContextPath() + PagePath.ERROR_PAGE);
                }
            }
        } catch (CommandException e) {
            request.setAttribute(RequestAttribute.EXCEPTION, e);
            request.getRequestDispatcher(PagePath.ERROR_PAGE).forward(request, response);
        }
    }

    public void destroy() {
    }
}