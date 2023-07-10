package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.User;
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
            images.add(imageService.findByCategoryId(category.getId()));
        }

        req.setAttribute(CATEGORIES.getValue(), categories);
        req.setAttribute(IMAGES.getValue(), images);
    }

    public static void showProducts(HttpServletRequest req, ProductService productService, ImageService imageService) {
        int categoryId = Integer.parseInt(req.getParameter(CATEGORY_ID.getValue()));

        List<Product> products = productService.findByCategoryId(categoryId);
        List<List<Image>> images = new ArrayList<>();

        for (Product product : products) {
            images.add(imageService.findByProductId(product.getId()));
        }

        req.setAttribute(PRODUCTS.getValue(), products);
        req.setAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));
    }

    public static void showProduct(HttpServletRequest req, ProductService productService, ImageService imageService) {
        int productId = Integer.parseInt(req.getParameter(PRODUCT_ID.getValue()));

        req.setAttribute(PRODUCT.getValue(), productService.findById(productId));
        req.setAttribute(IMAGES.getValue(), imageService.findByProductId(productId));
    }

    public static void showUserData(HttpServletRequest req, OrderService orderService, User user) {
        req.setAttribute(RequestParamsEnum.NAME.getValue(), user.getName());
        req.setAttribute(RequestParamsEnum.SURNAME.getValue(), user.getSurname());
        req.setAttribute(RequestParamsEnum.BIRTHDAY.getValue(), user.getBirthday().toString());
        req.setAttribute(RequestParamsEnum.EMAIL.getValue(), user.getEmail());
        req.setAttribute(RequestParamsEnum.ORDERS.getValue(), orderService.read());
    }
}