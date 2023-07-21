package by.teachmeskills.shop.controllers;

import by.teachmeskills.shop.enums.ShopConstants;
import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.services.CartService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.shop.enums.ShopConstants.SHOPPING_CART;
    import static by.teachmeskills.shop.enums.ShopConstants.USER;

@RestController
@SessionAttributes({SHOPPING_CART, USER})
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/add")
    public ModelAndView addProductToCart(@RequestParam(ShopConstants.PRODUCT_ID_PARAM) String id, @ModelAttribute(SHOPPING_CART) Cart shopCart) {
        int productId = Integer.parseInt(id);
        return cartService.addProductToCart(productId, shopCart);
    }
    @GetMapping("/remove")
    public ModelAndView removeProductFromCart(@RequestParam(ShopConstants.PRODUCT_ID_PARAM) String id, @ModelAttribute(SHOPPING_CART) Cart shopCart) {
        int productId = Integer.parseInt(id);
        return cartService.removeProductFromCart(productId, shopCart);
    }

    @GetMapping("/open")
    public ModelAndView redirectToShoppingCart(@ModelAttribute(SHOPPING_CART) Cart shopCart) {
        return cartService.showCartProductList(shopCart);
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(@ModelAttribute(SHOPPING_CART) Cart shopCart, User user) {
        return cartService.checkout(shopCart, user);
    }

    @ModelAttribute(SHOPPING_CART)
    public Cart shoppingCart() {
        return new Cart();
    }
}
