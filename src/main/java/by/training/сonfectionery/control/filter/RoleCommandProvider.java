package by.training.сonfectionery.control.filter;

import static by.training.сonfectionery.control.command.CommandType.*;

import by.training.сonfectionery.control.command.CommandType;
import by.training.сonfectionery.control.command.SessionAttribute;
import by.training.сonfectionery.control.command.impl.*;
import by.training.сonfectionery.domain.User;

import java.util.EnumSet;

class RoleCommandProvider {

    private static RoleCommandProvider instance;

    private EnumSet<CommandType> guestCommands = EnumSet.of(
            DEFAULT,
            LOGIN,

            CHANGE_LOCALE,
            EDIT_USER,
            REGISTRATION,
            MAIN_PAGE,
            LOGOUT,
            VERIFICATION,
            ACCEPT_ORDER,
            REJECT_ORDER,
            DONE_ORDER,
            SEARCH_PRODUCTS,
            CHECKOUT_COMMAND,
            CATALOG_PAGE,
            USERS_PAGE,
            SEARCH_USERS,
            SEARCH_ORDERS,
            ORDERS_PAGE,
            CREATE_PRODUCT_PAGE,
            CREATE_PRODUCT,

            LOGIN_PAGE,
            PROFILE_PAGE,
            REGISTRATION_PAGE,
            CONFIRMATION_PAGE,
            CART_PAGE
    );

    private EnumSet<CommandType> userCommands = EnumSet.of(
            DEFAULT,
            LOGIN,
            USERS_PAGE,
            CHANGE_LOCALE,
            SEARCH_USERS,
            REGISTRATION,
            MAIN_PAGE,
            LOGOUT,
            VERIFICATION,
            ACCEPT_ORDER,
            REJECT_ORDER,
            DONE_ORDER,
            SEARCH_PRODUCTS,
            CHECKOUT_COMMAND,
            CATALOG_PAGE,
            EDIT_USER,
            ORDERS_PAGE,


            LOGIN_PAGE,
            PROFILE_PAGE,
            REGISTRATION_PAGE,
            CONFIRMATION_PAGE,
            CART_PAGE
    );

    private EnumSet<CommandType> adminCommands = EnumSet.of(
            DEFAULT,
            LOGIN,
            USERS_PAGE,
            CHANGE_LOCALE,
            SEARCH_USERS,
            SEARCH_ORDERS,
            REGISTRATION,
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
            EDIT_USER
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
        boolean checkFlag = false;
        switch (role) {
            case GUEST:
                checkFlag = guestCommands.contains(commandType);
                break;
            case USER:
                checkFlag = userCommands.contains(commandType);
                break;
            case ADMIN:
                checkFlag = adminCommands.contains(commandType);
                break;
        }
        return checkFlag;
    }


}

