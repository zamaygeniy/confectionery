package by.training.сonfectionery.control.command;

public final class PagePath {
    public static final String ERROR_PAGE = "/jsp/error/error.jsp";
    public static final String LOGIN_PAGE = "/jsp/login.jsp";
    public static final String PROFILE_PAGE = "/jsp/profile.jsp";
    public static final String MAIN_PAGE = "/jsp/main.jsp";
    public static final String REGISTRATION_PAGE = "/jsp/registration.jsp";
    public static final String CONFIRMATION_PAGE = "/jsp/confirmation.jsp";
    public static final String CATALOG_PAGE = "/jsp/catalog.jsp";
    public static final String CREATE_PRODUCT_PAGE = "/jsp/create_product.jsp";
    public static final String CART_PAGE = "/jsp/cart.jsp";
    public static final String ORDERS_PAGE = "/jsp/orders.jsp";
    public static final String USERS_PAGE = "/jsp/users.jsp";


    public static final String GO_TO_CREATE_PRODUCT_PAGE = "/controller?command=create_product_page";
    public static final String GO_TO_ORDERS_PAGE = "/controller?command=orders_page";
    public static final String GO_TO_USERS_PAGE = "/controller?command=users_page";

    public static final String GO_TO_LOGIN_PAGE = "/controller?command=login_page";
    public static final String GO_TO_MAIN_PAGE = "/controller?command=main_page";
    public static final String GO_TO_CONFIRMATION_PAGE ="/controller?command=confirmation_page";
    public static final String GO_TO_PROFILE_PAGE = "/controller?command=profile_page";
    public static final String GO_TO_CART_PAGE = "/controller?command=cart_page";

    private PagePath(){}
}