package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.repositories.CategoryRepository;
import by.teachmeskills.shop.repositories.ImageRepository;
import by.teachmeskills.shop.repositories.Impl.CategoryRepositoryImpl;
import by.teachmeskills.shop.repositories.Impl.ImageRepositoryImpl;
import by.teachmeskills.shop.repositories.Impl.UserRepositoryImpl;
import by.teachmeskills.shop.repositories.UserRepository;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import by.teachmeskills.shop.utils.RequestDataGetter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.IMAGES;

public class LoginCommandImpl implements BaseCommand {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ImageRepository imageRepository = new ImageRepositoryImpl();
    private final static Logger log = LoggerFactory.getLogger(LoginCommandImpl.class);
    private final String WELCOME_INFO = "Добро пожаловать, ";
    private final String USER_NOT_FOUND_INFO = "Пользователя с таким логином не существует. Пожалуйста, введите данные повторно либо перейдите на страницу регистрации.";
    private final String PASSWORD_INCORRECT_INFO = "Введен неверный пароль. Повторите попытку.";

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        validateCredentials(userData.get("email"), userData.get("password"));

        User user = userRepository.findByEmailAndPassword(userData);

        String varInfo;
        if (user == null) {
            varInfo = USER_NOT_FOUND_INFO;
            req.setAttribute(RequestParamsEnum.INFO.getValue(), varInfo);
            return PagesPathEnum.LOGIN_PAGE.getPath();
        }

        if (!user.getPassword().equals(userData.get("password"))) {
            log.info("Wrong password entered.");
            varInfo = PASSWORD_INCORRECT_INFO;
            req.setAttribute(RequestParamsEnum.INFO.getValue(), varInfo);
            return PagesPathEnum.LOGIN_PAGE.getPath();
        }

        HttpSession session = req.getSession();
        session.setAttribute(RequestParamsEnum.USER.getValue(), user);

        varInfo = WELCOME_INFO + user.getName() + ".";
        req.setAttribute(RequestParamsEnum.INFO.getValue(), varInfo);
        log.info("Successful login '" + user.getEmail() + "'.");

        List<Category> categories = categoryRepository.read();
        List<Image> images = new ArrayList<>();
        for (Category category : categories) {
            images.add(imageRepository.findByCategoryId(category.getId()));
        }

        req.setAttribute(CATEGORIES.getValue(), categories);
        req.setAttribute(IMAGES.getValue(), images);
        return PagesPathEnum.HOME_PAGE.getPath();
    }

    private void validateCredentials(String email, String password) {
        try {
            HttpRequestCredentialsValidator.validateCredential(email);
            HttpRequestCredentialsValidator.validateCredential(password);
        } catch (RequestCredentialsNullException e) {
            log.error(e.getMessage());
        }
    }
}
