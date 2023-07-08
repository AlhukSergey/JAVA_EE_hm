package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.repositories.ImageRepository;
import by.teachmeskills.shop.repositories.Impl.ImageRepositoryImpl;
import by.teachmeskills.shop.repositories.Impl.ProductRepositoryImpl;
import by.teachmeskills.shop.repositories.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.*;

public class RedirectToProductPageCommandImpl implements BaseCommand {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final ImageRepository imageRepository = new ImageRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        int productId = Integer.parseInt(req.getParameter(PRODUCT_ID.getValue()));
        req.setAttribute(PRODUCT.getValue(), productRepository.findById(productId));
        req.setAttribute(IMAGES.getValue(), imageRepository.findByProductId(productId));
        return PagesPathEnum.PRODUCT_PAGE.getPath();
    }
}
