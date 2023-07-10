package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.Impl.ImageServiceImpl;
import by.teachmeskills.shop.utils.PageFiller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.teachmeskills.shop.commands.enums.PagesPathEnum.SHOPPING_CART_PAGE;

public class RedirectToShoppingCartPageCommandImpl implements BaseCommand {
    private final ImageService imageService = new ImageServiceImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(RequestParamsEnum.SHOPPING_CART.getValue());

        if (shoppingCart == null) {
            req.setAttribute(RequestParamsEnum.SHOPPING_CART_PRODUCTS.getValue(), "");
        } else {
            PageFiller.showShoppingCartProducts(req, shoppingCart, imageService);
        }

        return SHOPPING_CART_PAGE.getPath();
    }
}
