package by.training.сonfectionery.control.command;

public final class RequestAttribute {


    public static final String SUCCESSFUL_EDIT_DATA = "successfulEditMessage";
    public static final String SUCCESSFUL_PASSWORD_EDIT = "successfulPasswordEditMessage";
    public static final String SUCCESSFUL_REGISTRATION = "successfulRegistrationMessage";
    public static final String ERROR_USER_NON_ACTIVETED = "errorNonActivatedMessage";
    public static final String ERROR_WRONG_DATA = "errorWrongDataMessage";
    public static final String ERROR_USER_BLOCKED = "errorBlockedMessage";
    public static final String ERROR_WRONG_PASSWORD = "errorWrongPasswordMessage";
    public static final String ERROR_EMAIL_EXISTS = "errorEmailExistsMessage";

    public static final String ERROR_NO_PRODUCTS_FOUND = "errorNoProductsFound";
    public static final String ERROR_NO_ORDERS_FOUND = "errorNoOrdersFound";
    public static final String ERROR_NO_USERS_FOUND = "errorNoUsersFound";

    public static final String WRONG_CREATE_PRODUCT_DATA = "errorWrongDataMessage";
    public static final String WRONG_PASSWORD_OR_EMAIL = "errorLogInMessage";
    public static final String WRONG_REGISTRATION_DATA = "errorWrongDataMessage";

    public static final String REGISTRATION_USER_DATA = "userMap";
    public static final String CREATE_PRODUCT_DATA = "createProductData";
    public static final String PRODUCT_LIST = "productList";
    public static final String ORDERS_MAP = "ordersMap";
    public static final String USER_LIST = "userList";
    public static final String NUMBER_OF_PAGES = "numberOfPages";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE = "page";


    public static final int RECORDS_PER_PAGE = 12;
    public static final String EXCEPTION = "exception";


    private RequestAttribute() {
    }
}
