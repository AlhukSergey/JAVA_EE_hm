package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.utils.CRUDUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORY_ID;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCTS;

public class RedirectToCategoryPageImpl implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        String categoryId = req.getParameter(CATEGORY_ID.getValue());

        List<Product> products = CRUDUtils.getCategoryProducts(categoryId);
        req.setAttribute(PRODUCTS.getValue(), products);

        return PagesPathEnum.CATEGORY_PAGE.getPath();
    }
}
