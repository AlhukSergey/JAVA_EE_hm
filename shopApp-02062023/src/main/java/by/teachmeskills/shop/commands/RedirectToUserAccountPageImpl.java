package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.Impl.OrderServiceImpl;
import by.teachmeskills.shop.services.OrderService;
import by.teachmeskills.shop.utils.PageFiller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RedirectToUserAccountPageImpl implements BaseCommand {
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(RequestParamsEnum.USER.getValue());

        PageFiller.showUserData(req, orderService, user);
        return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
    }
}
