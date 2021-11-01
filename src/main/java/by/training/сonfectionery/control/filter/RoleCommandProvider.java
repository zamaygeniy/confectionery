package by.training.сonfectionery.control.filter;

import static by.training.сonfectionery.control.command.CommandType.*;
import static by.training.сonfectionery.control.command.UploadCommandType.*;

import by.training.сonfectionery.control.command.CommandType;
import by.training.сonfectionery.control.command.UploadCommandType;
import by.training.сonfectionery.domain.User;

import java.util.EnumSet;

class RoleCommandProvider {

    private static RoleCommandProvider instance;

    private final EnumSet<UploadCommandType> guestUploadCommands = EnumSet.of(
            UploadCommandType.DEFAULT,
            REGISTRATION,
            CHANGE_USER_IMAGE
    );

    private final EnumSet<UploadCommandType> userUploadCommands = EnumSet.of(
            UploadCommandType.DEFAULT,
            REGISTRATION,
            CHANGE_USER_IMAGE
    );

    private final EnumSet<UploadCommandType> adminUploadCommands = EnumSet.of(
            UploadCommandType.DEFAULT,
            REGISTRATION,
            CREATE_PRODUCT,
            CHANGE_USER_IMAGE

    );


    private final EnumSet<CommandType> guestCommands = EnumSet.of(
            CommandType.DEFAULT,
            LOGIN,
            CHANGE_LOCALE,
            MAIN_PAGE,
            LOGOUT,
            VERIFICATION,
            SEARCH_PRODUCTS,
            CATALOG_PAGE,
            LOGIN_PAGE,
            REGISTRATION_PAGE,
            CONFIRMATION_PAGE,
            CART_PAGE
    );

    private final EnumSet<CommandType> userCommands = EnumSet.of(
            CommandType.DEFAULT,
            LOGIN,
            CHANGE_LOCALE,
            MAIN_PAGE,
            LOGOUT,
            VERIFICATION,
            SEARCH_PRODUCTS,
            CHECKOUT_COMMAND,
            CATALOG_PAGE,
            EDIT_USER,
            CHANGE_PASSWORD,
            LOGIN_PAGE,
            PROFILE_PAGE,
            REGISTRATION_PAGE,
            CONFIRMATION_PAGE,
            USER_ORDERS,
            CART_PAGE
    );

    private final EnumSet<CommandType> adminCommands = EnumSet.of(
            CommandType.DEFAULT,
            LOGIN,
            CHANGE_PASSWORD,
            USERS_PAGE,
            CHANGE_LOCALE,
            SEARCH_USERS,
            SEARCH_ORDERS,
            MAIN_PAGE,
            LOGOUT,
            VERIFICATION,
            ACCEPT_ORDER,
            REJECT_ORDER,
            DONE_ORDER,
            SEARCH_PRODUCTS,
            CHECKOUT_COMMAND,
            CATALOG_PAGE,
            ORDERS_PAGE,
            LOGIN_PAGE,
            PROFILE_PAGE,
            REGISTRATION_PAGE,
            CONFIRMATION_PAGE,
            CREATE_PRODUCT_PAGE,
            CART_PAGE,
            EDIT_USER,
            BLOCK_USER,
            UNBLOCK_USER,
            USER_ORDERS,
            MAKE_ADMIN
    );

    private RoleCommandProvider() {
    }

    public static RoleCommandProvider getInstance() {
        if (instance == null) {
            instance = new RoleCommandProvider();
        }
        return instance;
    }

    public boolean checkCommand(User.Role role, CommandType commandType) {
        return switch (role) {
            case GUEST -> guestCommands.contains(commandType);
            case USER -> userCommands.contains(commandType);
            case ADMIN -> adminCommands.contains(commandType);
        };
    }


    public boolean checkUploadCommand(User.Role role, UploadCommandType uploadCommandType) {
        return switch (role) {
            case GUEST -> guestUploadCommands.contains(uploadCommandType);
            case USER -> userUploadCommands.contains(uploadCommandType);
            case ADMIN -> adminUploadCommands.contains(uploadCommandType);
        };
    }


}

