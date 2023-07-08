package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.repositories.ImageRepository;
import by.teachmeskills.shop.repositories.Impl.ImageRepositoryImpl;
import by.teachmeskills.shop.repositories.Impl.ProductRepositoryImpl;
import by.teachmeskills.shop.repositories.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.*;

public class RedirectToCategoryPageImpl implements BaseCommand {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final ImageRepository imageRepository = new ImageRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        int categoryId = Integer.parseInt(req.getParameter(CATEGORY_ID.getValue()));
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<List<Image>> images = new ArrayList<>();
        for (Product product : products) {
            images.add(imageRepository.findByProductId(product.getId()));
        }
        req.setAttribute(PRODUCTS.getValue(), products);
        req.setAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));
        return PagesPathEnum.CATEGORY_PAGE.getPath();
    }
}
