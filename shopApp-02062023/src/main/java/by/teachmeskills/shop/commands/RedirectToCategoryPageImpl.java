package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.teachmeskills.shop.utils.PageFiller.showProducts;

public class RedirectToCategoryPageImpl implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        showProducts(req);
        return PagesPathEnum.CATEGORY_PAGE.getPath();
    }
}
