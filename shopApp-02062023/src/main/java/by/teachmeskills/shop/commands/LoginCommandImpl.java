package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.InfoEnum;
import by.teachmeskills.shop.commands.enums.MapKeysEnum;
import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.services.CategoryService;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.Impl.CategoryServiceImpl;
import by.teachmeskills.shop.services.Impl.ImageServiceImpl;
import by.teachmeskills.shop.services.Impl.UserServiceImpl;
import by.teachmeskills.shop.services.UserService;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import by.teachmeskills.shop.utils.PageFiller;
import by.teachmeskills.shop.utils.RequestDataGetter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginCommandImpl implements BaseCommand {
    private final UserService userService = new UserServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();
    private final static Logger log = LoggerFactory.getLogger(LoginCommandImpl.class);

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        validateCredentials(userData.get(MapKeysEnum.EMAIL.getKey()), userData.get(MapKeysEnum.PASSWORD.getKey()));

        User user = userService.getUserByEmailAndPassword(userData);

        if (user == null) {
            req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.USER_NOT_FOUND_INFO.getInfo());

            return PagesPathEnum.LOGIN_PAGE.getPath();
        }

        if (!user.getPassword().equals(userData.get(MapKeysEnum.PASSWORD.getKey()))) {
            log.info("Wrong password entered.");

            req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.PASSWORD_INCORRECT_INFO.getInfo());
            return PagesPathEnum.LOGIN_PAGE.getPath();
        }

        HttpSession session = req.getSession();
        session.setAttribute(RequestParamsEnum.USER.getValue(), user);

        req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.WELCOME_INFO.getInfo() + user.getName() + ".");

        log.info("Successful login '" + user.getEmail() + "'.");

        PageFiller.showCategories(req, categoryService, imageService);
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
