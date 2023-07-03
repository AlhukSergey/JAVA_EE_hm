package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.teachmeskills.shop.utils.PageFiller.showUserData;

public class RedirectToUserAccountPageImpl implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        showUserData(req);
        return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
    }
}
