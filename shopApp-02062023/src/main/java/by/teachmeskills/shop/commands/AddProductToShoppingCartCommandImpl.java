package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.utils.CRUDUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.teachmeskills.shop.commands.enums.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT_ID;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SHOPPING_CART;

public class AddProductToShoppingCartCommandImpl implements BaseCommand{
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        String productId = req.getParameter(PRODUCT_ID.getValue());

        Cart shoppCart;
        Object sessionShoppCart = session.getAttribute(SHOPPING_CART.getValue());
        if(sessionShoppCart != null) {
            shoppCart = (Cart) sessionShoppCart;
        } else {
            shoppCart = new Cart();
            session.setAttribute(SHOPPING_CART.getValue(), shoppCart);
        }

        Product product = CRUDUtils.getProductById(productId);
        shoppCart.addProduct(product);
        req.setAttribute(PRODUCT.getValue(), product);

        return PRODUCT_PAGE.getPath();
    }
}