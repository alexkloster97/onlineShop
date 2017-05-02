package by.bsuir.shop.config;

import by.bsuir.shop.service.ServiceException;

import java.util.ResourceBundle;

/**
 * Jsp path config class
 */
public class JspConfig {
    public static final String INDEX = "index";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String REGISTER_OK = "register.ok";
    public static final String ERROR = "error";

    public static final String ADMIN = "admin";
    public static final String ADMIN_USER = "admin.user";
    public static final String ADMIN_CATEGORY = "admin.category";
    public static final String ADMIN_GOODS = "admin.goods";
    public static final String ADMIN_ORDERS = "admin.orders";

    public static final String GOODS_BY_CATEGORY = "goods.category";
    public static final String CART = "goods.cart";

    private static final String JSP_BOUNDLE_NAME = "jsp";
    private static ResourceBundle bundle = ResourceBundle.getBundle(JSP_BOUNDLE_NAME);

    /**
     * Reads property from jsp.properties
     *
     * @param key   property to read
     * @return      value of property
     */
    public static String getProperty(String key) {
        if(bundle == null) {
            bundle = ResourceBundle.getBundle(JSP_BOUNDLE_NAME);
        }

        return bundle.getString(key);
    }
}
