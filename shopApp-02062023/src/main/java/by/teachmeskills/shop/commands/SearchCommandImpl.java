package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.InfoEnum;
import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.Impl.ImageServiceImpl;
import by.teachmeskills.shop.services.Impl.ProductServiceImpl;
import by.teachmeskills.shop.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.IMAGES;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PRODUCTS;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.SEARCH_PARAM;

public class SearchCommandImpl implements BaseCommand {
    private final ProductService productService = new ProductServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {

        String searchParameter = req.getParameter(SEARCH_PARAM.getValue().toLowerCase());

        List<Product> products = productService.getProductsBySearchParameter(searchParameter);

        if (!products.isEmpty()) {
            List<List<Image>> images = new ArrayList<>();

            for (Product product : products) {
                images.add(imageService.getImagesByProductId(product.getId()));
            }

            req.setAttribute(PRODUCTS.getValue(), products);
            req.setAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));

            return PagesPathEnum.SEARCH_PAGE.getPath();
        }

        req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.PRODUCTS_NOT_FOUND_INFO.getInfo());
        return PagesPathEnum.SEARCH_PAGE.getPath();
    }
}
