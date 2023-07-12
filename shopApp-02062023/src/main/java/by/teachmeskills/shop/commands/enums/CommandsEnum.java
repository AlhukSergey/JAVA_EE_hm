package by.teachmeskills.shop.commands.enums;

public enum CommandsEnum {
    SHOW_LOGIN_PAGE_COMMAND("show-login-page"),
    REGISTRATION_USER_COMMAND("registration-user"),
    LOGIN_COMMAND("login"),
    REDIRECT_TO_REGISTRATION_PAGE_COMMAND("redirect-to-registration-page"),
    REDIRECT_TO_HOME_PAGE_COMMAND("redirect-to-home-page"),
    REDIRECT_TO_CATEGORY_PAGE_COMMAND("redirect-to-category-page"),
    REDIRECT_TO_PRODUCT_PAGE_COMMAND("redirect-to-product-page"),
    REDIRECT_TO_SHOPPING_CART_PAGE_COMMAND("redirect-to-shopping-cart-page"),
    REDIRECT_TO_MY_PAGE_COMMAND("redirect-to-my-page"),
    ADD_PRODUCT_TO_SHOPPING_CART_COMMAND("add-product-to-shopping-cart"),
    REMOVE_PRODUCT_FROM_SHOPPING_CART_COMMAND("remove-product-from-shopping-cart"),
    REDIRECT_TO_USER_ACCOUNT_PAGE_COMMAND("redirect-to-user-account-page"),
    CHECKOUT_COMMAND("checkout"),
    UPDATE_USER_DATA_COMMAND("update-user-data"),
    REDIRECT_TO_SEARCH_PAGE_COMMAND("redirect-to-search-page"),
    SEARCH_COMMAND("search");
    private final String command;

    CommandsEnum(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
