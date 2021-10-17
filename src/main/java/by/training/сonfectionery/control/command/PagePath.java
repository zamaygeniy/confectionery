package by.training.сonfectionery.control.command;

public final class PagePath {
    public static final String ERROR_404_PAGE = "/jsp/error/404.jsp";
    public static final String LOGIN_PAGE = "/jsp/login.jsp";
    public static final String PROFILE_PAGE = "/jsp/profile.jsp";
    public static final String MAIN_PAGE = "/jsp/main.jsp";
    public static final String REGISTRATION_PAGE = "/jsp/registration.jsp";
    public static final String CONFIRMATION_PAGE = "/jsp/confirmation.jsp";
    public static final String EDIT_PROFILE_PAGE = "/jsp/edit_profile.jsp";
    public static final String PRODUCT_PAGE = "/jsp/products.jsp";
    public static final String CATALOG_PAGE = "/jsp/catalog.jsp";
    public static final String GO_TO_EDIT_PROFILE_PAGE = "/controller?command=edit_profile_page";
    public static final String CART_PAGE = "/jsp/cart.jsp";
    public static final String CHECKOUT = "/controller?command=checkout_command";
    public static final String ORDERS_PAGE = "/jsp/orders.jsp";

    private PagePath(){}
}