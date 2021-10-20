package by.training.сonfectionery.control.command;

import by.training.сonfectionery.control.command.impl.*;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    LOGIN(new LoginCommand()),

    CHANGE_LOCALE(new ChangeLocaleCommand()),

    REGISTRATION(new RegistrationCommand()),
    MAIN_PAGE(new GoToMainPage()),
    LOGOUT(new LogOutCommand()),
    VERIFICATION(new VerificationCommand()),
    ACCEPT_ORDER(new AcceptOrderCommand()),
    REJECT_ORDER(new RejectOrderCommand()),
    DONE_ORDER(new DoneOrderCommand()),
    SEARCH_PRODUCTS(new SearchProductsCommand()),
    SEARCH_ORDERS(new SearchOrdersCommand()),
    CHECKOUT_COMMAND(new CheckoutCommand()),
    CATALOG_PAGE(new SearchProductsCommand()),
    ORDERS_PAGE(new SearchOrdersCommand()),

    LOGIN_PAGE(new GoToLoginPage()),
    PROFILE_PAGE(new GoToProfilePage()),
    REGISTRATION_PAGE(new GoToRegistrationPage()),
    CONFIRMATION_PAGE(new GoToConfirmationPage()),
    EDIT_PROFILE_PAGE(new GoToEditProfilePage()),
    CART_PAGE(new GoToCartPage());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
