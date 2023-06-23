package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.utils.CRUDUtils;
import jakarta.servlet.http.HttpServletRequest;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT_ID;

public class RedirectToProductPageCommandImpl implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        String productId = req.getParameter(PRODUCT_ID.getValue());

        Product product = CRUDUtils.getProductById(productId);
        req.setAttribute(PRODUCT.getValue(), product);

        return PagesPathEnum.PRODUCT_PAGE.getPath();
    }
}
