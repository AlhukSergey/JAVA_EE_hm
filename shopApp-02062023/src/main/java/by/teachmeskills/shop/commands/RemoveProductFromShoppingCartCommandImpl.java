package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.teachmeskills.shop.commands.enums.PagesPathEnum.SHOPPING_CART_PAGE;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT_ID;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SHOPPING_CART;

public class RemoveProductFromShoppingCartCommandImpl implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        String productId = req.getParameter(PRODUCT_ID.getValue());

        Cart shoppCart = (Cart) session.getAttribute(SHOPPING_CART.getValue());

        shoppCart.removeProduct(Integer.parseInt(productId));

        return SHOPPING_CART_PAGE.getPath();
    }
}
