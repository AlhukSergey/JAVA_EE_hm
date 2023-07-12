package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.*;
import by.teachmeskills.shop.services.CategoryService;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.OrderService;
import by.teachmeskills.shop.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORY_ID;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.IMAGES;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCTS;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT_ID;

public class PageFiller {
    private PageFiller() {
    }

    public static void showCategories(HttpServletRequest req, CategoryService categoryService, ImageService imageService) {
        List<Category> categories = categoryService.read();
        List<Image> images = new ArrayList<>();

        for (Category category : categories) {
            images.add(imageService.getImageByCategoryId(category.getId()));
        }

        req.setAttribute(CATEGORIES.getValue(), categories);
        req.setAttribute(IMAGES.getValue(), images);
    }

    public static void showProducts(HttpServletRequest req, ProductService productService, ImageService imageService) {
        int categoryId = Integer.parseInt(req.getParameter(CATEGORY_ID.getValue()));

        List<Product> products = productService.getProductsByCategoryId(categoryId);
        List<List<Image>> images = new ArrayList<>();

        for (Product product : products) {
            images.add(imageService.getImagesByProductId(product.getId()));
        }

        req.setAttribute(PRODUCTS.getValue(), products);
        req.setAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));
    }

    public static void showProduct(HttpServletRequest req, ProductService productService, ImageService imageService) {
        int productId = Integer.parseInt(req.getParameter(PRODUCT_ID.getValue()));

        req.setAttribute(PRODUCT.getValue(), productService.getProductById(productId));
        req.setAttribute(IMAGES.getValue(), imageService.getImagesByProductId(productId));
    }

    public static void showShoppingCartProducts(HttpServletRequest req, Cart shoppingCart, ImageService imageService) {
        List<Product> products = shoppingCart.getProducts();
        List<List<Image>> images = new ArrayList<>();

        for (Product product : products) {
            images.add(imageService.getImagesByProductId(product.getId()));
        }

        req.setAttribute(RequestParamsEnum.SHOPPING_CART_PRODUCTS.getValue(), products);
        req.setAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));
    }

    public static void showUserData(HttpServletRequest req, OrderService orderService, User user) {
        req.setAttribute(RequestParamsEnum.NAME.getValue(), user.getName());
        req.setAttribute(RequestParamsEnum.SURNAME.getValue(), user.getSurname());
        req.setAttribute(RequestParamsEnum.BIRTHDAY.getValue(), user.getBirthday().toString());
        req.setAttribute(RequestParamsEnum.EMAIL.getValue(), user.getEmail());
        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        req.setAttribute(RequestParamsEnum.ACTIVE_ORDERS.getValue(), orders.stream().filter(order -> order.getOrderStatus() == OrderStatus.ACTIVE).collect(Collectors.toList()));
        req.setAttribute(RequestParamsEnum.FINISHED_ORDERS.getValue(), orders.stream().filter(order -> order.getOrderStatus() == OrderStatus.FINISHED).collect(Collectors.toList()));
    }
}