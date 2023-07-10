package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.Impl.ImageServiceImpl;
import by.teachmeskills.shop.services.Impl.ProductServiceImpl;
import by.teachmeskills.shop.services.ProductService;
import by.teachmeskills.shop.utils.PageFiller;
import jakarta.servlet.http.HttpServletRequest;

public class RedirectToCategoryPageImpl implements BaseCommand {
    private final ProductService productService = new ProductServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        PageFiller.showProducts(req, productService, imageService);
        return PagesPathEnum.CATEGORY_PAGE.getPath();
    }
}
