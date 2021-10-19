package by.training.сonfectionery.control.command;

public final class RequestAttribute {

    public static final String SUCCESSFUL_EDIT_DATA = "successEditMessage";
    public static final String ERROR_WRONG_DATA = "errorWrongData";
    public static final String REGISTRATION_USER_DATA = "userMap";

    public static final String SUCCESS_REGISTRATION_MESSAGE = "successRegistrationMessage";
    public static final String ERROR_USER_NON_ACTIVETED = "errorNonActivatedMessage";
    public static final String ERROR_USER_BLOCKED = "errorBlockedMessage";
    public static final String WRONG_PASSWORD_OR_EMAIL = "errorLogInMessage";
    public static final String ERROR_NO_PRODUCT_TYPES = "errorNoProductTypes";
    public static final String WRONG_REGISTRATION_DATA = "errorWrongDataMessage";
    public static final String ERROR_EMAIL_EXISTS = "errorEmailExistsMessage";
    public static final String ERROR_NO_PRODUCTS_FOUND = "errorNoProductsFound";

    public static final String PAGE = "page";
    public static final String PRODUCT_LIST = "productList";
    public static final String NUMBER_OF_PAGES = "numberOfPages";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String ORDERS_MAP = "ordersMap";

    public static final int RECORDS_PER_PAGE = 12;


    private RequestAttribute() {
    }
}
