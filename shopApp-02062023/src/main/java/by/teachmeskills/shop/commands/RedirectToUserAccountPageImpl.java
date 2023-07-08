package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.domain.OrderStatus;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.repositories.Impl.OrderRepositoryImpl;
import by.teachmeskills.shop.repositories.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.ACTIVE_ORDERS;

public class RedirectToUserAccountPageImpl implements BaseCommand {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String varName = user.getName();
        String varSurname = user.getSurname();
        LocalDate varDate = user.getBirthday();
        String varEmail = user.getEmail();
        req.setAttribute("name", varName);
        req.setAttribute("surname", varSurname);
        req.setAttribute("birthday", varDate.toString());
        req.setAttribute("email", varEmail);

        req.setAttribute(ACTIVE_ORDERS.getValue(), orderRepository.getOrdersByUserIdAndStatus(user.getId(), OrderStatus.ACTIVE));
        return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
    }
}
