package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.domain.Cart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.teachmeskills.shop.commands.enums.PagesPathEnum.SHOPPING_CART_PAGE;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SHOPPING_CART;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SHOPPING_CART_PRODUCTS;

public class RedirectToShoppingCartPageCommandImpl implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(SHOPPING_CART.getValue());

        if (shoppingCart == null) {
            req.setAttribute(SHOPPING_CART_PRODUCTS.getValue(), "");
        } else {
            req.setAttribute(SHOPPING_CART_PRODUCTS.getValue(), shoppingCart.getProducts());
        }

        return SHOPPING_CART_PAGE.getPath();
    }
}
