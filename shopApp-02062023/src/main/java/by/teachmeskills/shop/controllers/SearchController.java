package by.teachmeskills.shop.controllers;

import by.teachmeskills.shop.enums.InfoEnum;
import by.teachmeskills.shop.enums.PagesPathEnum;
import by.teachmeskills.shop.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.ProductService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.enums.RequestParamsEnum.IMAGES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PRODUCTS;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ProductService productService;
    private final ImageService imageService;

    public SearchController(ProductService productService, ImageService imageService) {
        this.productService = productService;
        this.imageService = imageService;
    }

    @GetMapping
    public ModelAndView openSearchPage() {
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath());
    }

    @PostMapping("/findProducts")
    public ModelAndView search(@RequestParam("search_param") String searchParameter) {
        ModelMap model = new ModelMap();
        List<Product> products = productService.getProductsBySearchParameter(searchParameter);

        if (!products.isEmpty()) {
            List<List<Image>> images = new ArrayList<>();

            for (Product product : products) {
                images.add(imageService.getImagesByProductId(product.getId()));
            }

            model.addAttribute(PRODUCTS.getValue(), products);
            model.addAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));

            return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
        }

        model.addAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.PRODUCTS_NOT_FOUND_INFO.getInfo());
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
    }
}
