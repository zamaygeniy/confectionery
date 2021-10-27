package by.training.сonfectionery.control;

import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.RequestAttribute;
import by.training.сonfectionery.control.command.RequestParameter;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.control.upload.UploadCommand;
import by.training.сonfectionery.control.upload.UploadCommandFactory;
import by.training.сonfectionery.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "FileUploadingServlet", value = "/uploadServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class FileUploadingServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UploadCommand command = UploadCommandFactory.getInstance().createCommand(request);
        Part filePart = request.getPart(RequestParameter.FILE);
        InputStream fileContent = filePart.getInputStream();
        Router router;
        try {
            router = command.execute(request, fileContent);
            switch (router.getRouteType()) {
                case FORWARD:
                    request.getRequestDispatcher(router.getPagePath()).forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(request.getContextPath() + router.getPagePath());
                    break;
                default:
                    logger.error("Incorrect route type " + router.getRouteType());
                    response.sendRedirect(request.getContextPath() + PagePath.ERROR_PAGE);
            }
        } catch (CommandException e) {
            request.setAttribute(RequestAttribute.EXCEPTION, e);
            request.getRequestDispatcher(PagePath.ERROR_PAGE).forward(request, response);
        }
        
        
        
    }
}
