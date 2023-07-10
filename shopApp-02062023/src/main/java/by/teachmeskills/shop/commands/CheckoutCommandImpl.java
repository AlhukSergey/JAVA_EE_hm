package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.InfoEnum;
import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.OrderStatus;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.Impl.OrderServiceImpl;
import by.teachmeskills.shop.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SHOPPING_CART;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.USER;

public class CheckoutCommandImpl implements BaseCommand {
    private final OrderService orderService = new OrderServiceImpl();
    private final static Logger log = LoggerFactory.getLogger(CheckoutCommandImpl.class);

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(SHOPPING_CART.getValue());
        User user = (User) session.getAttribute(USER.getValue());

        List<Product> productList = shoppingCart.getProducts();

        if (productList.isEmpty()) {
            req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.SHOP_CART_IS_EMPTY_INFO.getInfo());
            return PagesPathEnum.SHOPPING_CART_PAGE.getPath();
        }

        Order order = Order.builder()
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.ACTIVE)
                .price(shoppingCart.getTotalPrice())
                .build();

        orderService.create(order);

        log.info("Successful registration a new order.");

        shoppingCart.clear();
        return PagesPathEnum.SHOPPING_CART_PAGE.getPath();
    }
}
