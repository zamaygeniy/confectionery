package by.training.сonfectionery.control.upload;

import by.training.сonfectionery.control.upload.impl.DefaultUploadCommand;
import by.training.сonfectionery.control.upload.impl.UploadProductImageCommand;
import by.training.сonfectionery.control.upload.impl.UploadUserImageCommand;

public enum UploadCommandType {
    DEFAULT(new DefaultUploadCommand()),

    UPLOAD_USER_IMAGE(new UploadUserImageCommand()),

    UPLOAD_PRODUCT_IMAGE(new UploadProductImageCommand());

    private UploadCommand command;

    UploadCommandType(UploadCommand command) {
        this.command = command;
    }

    public UploadCommand getCommand() {
        return command;
    }
}
