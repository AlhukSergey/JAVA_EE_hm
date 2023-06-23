package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.domain.Category;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORIES;

public class HomePageFiller {
    private HomePageFiller(){}
    public static void showCategories(HttpServletRequest req) {
        List<Category> categories = CRUDUtils.getCategories();
        req.setAttribute(CATEGORIES.getValue(), categories);
    }
}
