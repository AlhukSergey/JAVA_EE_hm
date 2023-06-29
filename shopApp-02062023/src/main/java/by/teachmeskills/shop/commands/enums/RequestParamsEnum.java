package by.teachmeskills.shop.commands.enums;

public enum RequestParamsEnum {
    COMMAND("command"),
    NAME("name"),
    SURNAME("surname"),
    BIRTHDAY("birthday"),
    EMAIL("email"),
    LOGIN("email"),
    PASSWORD("password"),
    USER("user"),
    INFO("info"),
    CATEGORIES("categories"),
    CATEGORY("category"),
    CATEGORY_ID("category_id"),
    PRODUCTS("products"),
    PRODUCT("product"),
    PRODUCT_ID("product_id"),
    SHOPPING_CART("shopping-cart"),
    SHOPPING_CART_PRODUCTS("cartProductsList"),
    OLD_PASSWORD("oldPassword"),
    NEW_PASSWORD("newPassword"),
    NEW_PASSWORD_REP("newPasswordRep"),
    ACTIVE_ORDERS("activeOrders");

    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
