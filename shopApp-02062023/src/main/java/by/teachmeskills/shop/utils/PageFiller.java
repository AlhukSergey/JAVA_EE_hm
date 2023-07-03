package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.ACTIVE_ORDERS;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORY_ID;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCTS;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT_ID;

public class PageFiller {
    private PageFiller(){}
    public static void showCategories(HttpServletRequest req) {
        List<Category> categories = CRUDUtils.getCategories();
        req.setAttribute(CATEGORIES.getValue(), categories);
    }

    public static void showProducts(HttpServletRequest req) {
        String categoryId = req.getParameter(CATEGORY_ID.getValue());
        List<Product> products = CRUDUtils.getCategoryProducts(categoryId);
        req.setAttribute(PRODUCTS.getValue(), products);
    }

    public static void showProduct(HttpServletRequest req) {
        String productId = req.getParameter(PRODUCT_ID.getValue());
        Product product = CRUDUtils.getProductById(productId);
        req.setAttribute(PRODUCT.getValue(), product);
    }

    public static void showUserData(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String varName = user.getName();
        String varSurname = user.getSurname();
        LocalDate varDate = user.getBirthday();
        String varEmail = user.getEmail();
        req.setAttribute("name", varName);
        req.setAttribute("surname", varSurname);
        req.setAttribute("birthday", varDate.toString());
        req.setAttribute("email", varEmail);

        List<Order> activeOrders = CRUDUtils.getActiveOrdersByUserId(user);
        req.setAttribute(ACTIVE_ORDERS.getValue(), activeOrders);
    }
}
