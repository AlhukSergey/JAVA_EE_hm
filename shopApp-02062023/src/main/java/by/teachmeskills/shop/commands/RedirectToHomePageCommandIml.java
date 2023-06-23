package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.teachmeskills.shop.utils.HomePageFiller.showCategories;

public class RedirectToHomePageCommandIml implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        showCategories(req);
        return PagesPathEnum.HOME_PAGE.getPath();
    }
}
