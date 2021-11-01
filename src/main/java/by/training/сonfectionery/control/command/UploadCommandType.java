package by.training.сonfectionery.control.command;

import by.training.сonfectionery.control.command.impl.admin.CreateProductCommand;
import by.training.сonfectionery.control.command.impl.DefaultUploadCommand;
import by.training.сonfectionery.control.command.impl.RegistrationCommand;
import by.training.сonfectionery.control.command.impl.ChangeUserImageCommand;

public enum UploadCommandType {
    DEFAULT(new DefaultUploadCommand()),

    REGISTRATION(new RegistrationCommand()),

    CREATE_PRODUCT(new CreateProductCommand()),

    CHANGE_USER_IMAGE(new ChangeUserImageCommand());

    private UploadCommand command;

    UploadCommandType(UploadCommand command) {
        this.command = command;
    }

    public UploadCommand getCommand() {
        return command;
    }
}
