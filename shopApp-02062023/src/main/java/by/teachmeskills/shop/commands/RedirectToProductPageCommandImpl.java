package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.teachmeskills.shop.utils.PageFiller.showProduct;

public class RedirectToProductPageCommandImpl implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        showProduct(req);
        return PagesPathEnum.PRODUCT_PAGE.getPath();
    }
}
