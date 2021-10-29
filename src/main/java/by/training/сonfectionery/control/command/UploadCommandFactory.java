package by.training.сonfectionery.control.command;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

public class UploadCommandFactory {
    private static UploadCommandFactory instance = new UploadCommandFactory();

    private UploadCommandFactory() {
    }


    public static UploadCommandFactory getInstance() {
        return instance;
    }


    public UploadCommand createCommand(HttpServletRequest request) {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        UploadCommand command;
        if (commandName == null) {
            return UploadCommandType.DEFAULT.getCommand();
        } else {
            try {
                UploadCommandType commandType = UploadCommandType.valueOf(commandName.toUpperCase(Locale.ROOT));
                command = commandType.getCommand();
            } catch (IllegalArgumentException e) {
                command = UploadCommandType.DEFAULT.getCommand();
            }
        }
        return command;
    }
}
