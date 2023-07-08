package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.repositories.CategoryRepository;
import by.teachmeskills.shop.repositories.ImageRepository;
import by.teachmeskills.shop.repositories.Impl.CategoryRepositoryImpl;
import by.teachmeskills.shop.repositories.Impl.ImageRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.IMAGES;

public class RedirectToHomePageCommandIml implements BaseCommand {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final ImageRepository imageRepository = new ImageRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        List<Category> categories = categoryRepository.read();
        List<Image> images = new ArrayList<>();
        for (Category category : categories) {
            images.add(imageRepository.findByCategoryId(category.getId()));
        }
        req.setAttribute(CATEGORIES.getValue(), categories);
        req.setAttribute(IMAGES.getValue(), images);
        return PagesPathEnum.HOME_PAGE.getPath();
    }
}
