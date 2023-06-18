package by.teachmeskills.shop.commands.enums;

public enum PagesPathEnum {
    REGISTRATION_PAGE("registration.jsp"),
    LOGIN_PAGE("login.jsp"),
    HOME_PAGE("home.jsp"),
    CATEGORY_PAGE("category.jsp"),
    PRODUCT_PAGE("product.jsp"),
    SHOPPING_CART_PAGE("shoppingCart.jsp"),
    MY_PAGE("myPage.html");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
