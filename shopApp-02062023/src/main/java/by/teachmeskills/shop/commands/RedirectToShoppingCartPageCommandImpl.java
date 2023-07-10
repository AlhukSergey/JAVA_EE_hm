package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.Impl.ImageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.commands.enums.PagesPathEnum.SHOPPING_CART_PAGE;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.IMAGES;

public class RedirectToShoppingCartPageCommandImpl implements BaseCommand {
    private final ImageService imageService = new ImageServiceImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(RequestParamsEnum.SHOPPING_CART.getValue());

        if (shoppingCart == null) {
            req.setAttribute(RequestParamsEnum.SHOPPING_CART_PRODUCTS.getValue(), "");
        } else {
            List<Product> products = shoppingCart.getProducts();
            List<List<Image>> images = new ArrayList<>();

            for (Product product : products) {
                images.add(imageService.findByProductId(product.getId()));
            }

            req.setAttribute(RequestParamsEnum.SHOPPING_CART_PRODUCTS.getValue(), products);
            req.setAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));
        }

        return SHOPPING_CART_PAGE.getPath();
    }
}
