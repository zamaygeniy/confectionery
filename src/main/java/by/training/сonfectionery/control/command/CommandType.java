package by.training.сonfectionery.control.command;

import by.training.сonfectionery.control.command.impl.*;
import by.training.сonfectionery.control.command.impl.admin.*;
import by.training.сonfectionery.control.command.impl.go.*;
import by.training.сonfectionery.control.command.impl.search.*;

public enum CommandType {
    DEFAULT(new DefaultCommand()),

    LOGIN(new LoginCommand()),
    LOGOUT(new LogOutCommand()),

    VERIFICATION(new VerificationCommand()),

    CHANGE_LOCALE(new ChangeLocaleCommand()),

    EDIT_USER(new EditUserCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),

    SEARCH_PRODUCTS(new SearchProductsCommand()),
    SEARCH_USERS(new SearchUsersCommand()),
    ACCEPT_ORDER(new AcceptOrderCommand()),
    REJECT_ORDER(new RejectOrderCommand()),
    DONE_ORDER(new DoneOrderCommand()),

    SEARCH_ORDERS(new SearchOrdersCommand()),
    CHECKOUT_COMMAND(new CheckoutCommand()),
    CATALOG_PAGE(new SearchProductsCommand()),
    ORDERS_PAGE(new SearchOrdersCommand()),
    USER_ORDERS(new SearchUserOrdersCommand()),
    USERS_PAGE(new SearchUsersCommand()),

    BLOCK_USER(new BlockUserCommand()),
    UNBLOCK_USER(new UnblockUserCommand()),
    MAKE_ADMIN(new MakeUserAdminCommand()),

    MAIN_PAGE(new GoToMainPage()),
    CREATE_PRODUCT_PAGE(new GoToCreateProductPage()),
    LOGIN_PAGE(new GoToLoginPage()),
    PROFILE_PAGE(new GoToProfilePage()),
    REGISTRATION_PAGE(new GoToRegistrationPage()),
    CONFIRMATION_PAGE(new GoToConfirmationPage()),
    CART_PAGE(new GoToCartPage());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
