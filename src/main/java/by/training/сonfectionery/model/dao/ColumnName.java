package by.training.сonfectionery.model.dao;

public class ColumnName {
    private ColumnName() {
    }

    public static final String ID = "id";

    //users table
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String IMAGE = "image";

    //feedback table
    public static final String TEXT = "text";
    public static final String DATE = "date";
    public static final String RATING = "rating";
    public static final String USER_ID = "user_id";

    public static final String ORDER_STATUS_ID = "order_status_id";
    public static final String ORDER_STATUS_NAME = "order_status_name";

    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String WEIGTH = "weight";
    public static final String NUMBER_IN_STOCK = "number_in_stock";

    public static final String PRODUCT_TYPE_ID = "product_type_id";
    public static final String PRODUCT_TYPE_NAME = "type_name";
    //role table
    public static final String ROLE_ID = "role_id";
    public static final String ROLE = "role";

    //status table
    public static final String STATUS_ID = "status_id";
    public static final String STATUS = "status";

}

