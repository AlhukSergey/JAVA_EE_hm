package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.Impl.ImageServiceImpl;
import by.teachmeskills.shop.services.Impl.ProductServiceImpl;
import by.teachmeskills.shop.services.ProductService;
import by.teachmeskills.shop.utils.PageFiller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.teachmeskills.shop.commands.enums.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCT_ID;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SHOPPING_CART;

public class AddProductToShoppingCartCommandImpl implements BaseCommand {
    private final ProductService productService = new ProductServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();

        Cart shoppCart;
        Object sessionShoppCart = session.getAttribute(SHOPPING_CART.getValue());
        if (sessionShoppCart != null) {
            shoppCart = (Cart) sessionShoppCart;
        } else {
            shoppCart = new Cart();
            session.setAttribute(SHOPPING_CART.getValue(), shoppCart);
        }

        shoppCart.addProduct(productService.findById(Integer.parseInt(req.getParameter(PRODUCT_ID.getValue()))));

        PageFiller.showProduct(req, productService, imageService);

        return PRODUCT_PAGE.getPath();
    }
}
