package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.InfoEnum;
import by.teachmeskills.shop.commands.enums.MapKeysEnum;
import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.exceptions.UserAlreadyExistsException;
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

import java.time.LocalDate;
import java.util.Map;

public class RegistrationUserCommandImpl implements BaseCommand {
    private final UserService userService = new UserServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();
    private final static Logger log = LoggerFactory.getLogger(RegistrationUserCommandImpl.class);

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        try {
            HttpRequestCredentialsValidator.validateUserData(userData);
            checkUserAlreadyExists(userData);
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.ERROR_DATA_INFO.getInfo() + e.getMessage());
            return PagesPathEnum.REGISTRATION_PAGE.getPath();
        } catch (UserAlreadyExistsException e) {
            req.setAttribute(RequestParamsEnum.INFO.getValue(), e.getMessage());
            return PagesPathEnum.REGISTRATION_PAGE.getPath();
        }

        User user = User.builder()
                .name(userData.get(MapKeysEnum.NAME.getKey()))
                .surname(userData.get(MapKeysEnum.SURNAME.getKey()))
                .birthday(LocalDate.parse(userData.get(MapKeysEnum.BIRTHDAY.getKey())))
                .email(userData.get(MapKeysEnum.EMAIL.getKey()))
                .password(userData.get(MapKeysEnum.PASSWORD.getKey()))
                .balance(0.00)
                .build();

        userService.create(user);

        log.info("Successful registration of a new user: '" + user.getEmail() + "'.");

        HttpSession session = req.getSession();
        session.setAttribute(RequestParamsEnum.USER.getValue(), user);

        req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.WELCOME_INFO.getInfo() + user.getName() + ".");

        PageFiller.showCategories(req, categoryService, imageService);
        return PagesPathEnum.HOME_PAGE.getPath();
    }

    private void checkUserAlreadyExists(Map<String, String> data) throws UserAlreadyExistsException {
        User user = userService.findByEmailAndPassword(data);
        if (user != null) {
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует. " +
                    "Чтобы войти в аккаунт, перейдите на страницу входа...");
        }
    }
}
