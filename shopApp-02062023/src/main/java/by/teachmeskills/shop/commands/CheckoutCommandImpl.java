package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.utils.CRUDUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SHOPPING_CART;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.USER;

public class CheckoutCommandImpl implements BaseCommand {
    private final static Logger log = LoggerFactory.getLogger(ChangeUserPasswordImpl.class);
    private final String IS_EMPTY_INFO = "В корзине еще нет продуктов. Чтобы оформить заказ, добавьте продукты.";
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(SHOPPING_CART.getValue());
        User user = (User) session.getAttribute(USER.getValue());

        List<Product> productList = shoppingCart.getProducts();
        String varInfo;

        if (productList.isEmpty()) {
            varInfo = IS_EMPTY_INFO;
            req.setAttribute(RequestParamsEnum.INFO.getValue(), varInfo);
            return PagesPathEnum.SHOPPING_CART_PAGE.getPath();
        }

        Order order = new Order(user, productList, shoppingCart.getTotalPrice());
        CRUDUtils.addOrder(order);
        log.info("Successful new order registration.");
        shoppingCart.clear();
        return PagesPathEnum.SHOPPING_CART_PAGE.getPath();
    }
}
