package by.teachmeskills.shop.controllers;

import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.enums.PagesPathEnum;
import by.teachmeskills.shop.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.services.CategoryService;
import by.teachmeskills.shop.services.ImageService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static by.teachmeskills.shop.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.IMAGES;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;
    private final ImageService imageService;

    public HomeController(CategoryService categoryService, ImageService imageService) {
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @GetMapping
    public ModelAndView openHomePage() {
        ModelMap model = new ModelMap();
        List<Category> categories = categoryService.read();
        List<Image> images = new ArrayList<>();

        for (Category category : categories) {
            images.add(imageService.getImageByCategoryId(category.getId()));
        }

        model.addAttribute(CATEGORIES.getValue(), categories);
        model.addAttribute(IMAGES.getValue(), images);

        model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categories);
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), model);
    }
}
