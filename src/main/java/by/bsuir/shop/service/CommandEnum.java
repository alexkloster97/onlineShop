package by.bsuir.shop.service;

/**
 * Enum of commands to execute
 */
public enum CommandEnum {
    NO_COMMAND("void"),
    LANGUAGE("language"),
    REGISTER("register"),
    REGISTER_START("register-start"),
    LOGIN("login"),
    LOGIN_START("login-start"),
    LOGOUT("logout"),
    USER_START("user-start"),
    USER_BANN("user-bann"),
    USER_RIGHTS("user-rights"),
    USER_DELETE("user-delete"),
    CATEGORY_START("category-start"),
    CATEGORY_ADD("category-add"),
    CATEGORY_EDIT("category-edit"),
    CATEGORY_DELETE("category-delete"),
    GOODS_START("good-start"),
    GOODS_ADD("goods-add"),
    GOODS_DELETE("goods-delete"),
    GOODS_EDIT_START("goods-edit-start"),
    GOODS_EDIT("goods-edit"),
    ORDER_START("order-start"),
    ORDER_DECLINE("order-decline"),
    ORDER_ACCEPT("order-accept"),
    ORDER_FILTER("order-filter"),
    GOODS_CATEGORY_LIST("goods-list-command"),
    CART_LIST("cart-list"),
    CART_SUBMIT("cart-submit"),
    CART_EDIT("cart-edit"),
    CART_DELETE("cart-delete"),
    CART_ADD("cart-add"),
    CART_FIND("cart-find");

    private String type;

    private CommandEnum(String type) {
        this.type = type;
    }

    /**
     * Get enum type according to string pType
     * @param pType     string to get enum type
     * @return          enum type
     */
    static public CommandEnum getType(String pType) {
        for (CommandEnum type: CommandEnum.values()) {
            if (type.getType().equals(pType)) {
                return type;
            }
        }

        return NO_COMMAND;
    }

    public String getType() {
        return type;
    }
}
