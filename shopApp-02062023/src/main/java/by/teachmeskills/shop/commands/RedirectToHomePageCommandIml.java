package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.services.CategoryService;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.Impl.CategoryServiceImpl;
import by.teachmeskills.shop.services.Impl.ImageServiceImpl;
import by.teachmeskills.shop.utils.PageFiller;
import jakarta.servlet.http.HttpServletRequest;

public class RedirectToHomePageCommandIml implements BaseCommand {
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        PageFiller.showCategories(req, categoryService, imageService);
        return PagesPathEnum.HOME_PAGE.getPath();
    }
}
