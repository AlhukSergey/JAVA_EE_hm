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
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.DateParser;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import by.teachmeskills.shop.utils.RequestDataGetter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static by.teachmeskills.shop.utils.PageFiller.showCategories;

public class RegistrationUserCommandImpl implements BaseCommand {
    private final static Logger log = LoggerFactory.getLogger(RegistrationUserCommandImpl.class);

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        try {
            HttpRequestCredentialsValidator.validateUserData(userData);
            checkUserAlreadyExists(userData.get(MapKeysEnum.EMAIL.getKey()));
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
                .birthday(DateParser.parseToDate(userData.get(MapKeysEnum.BIRTHDAY.getKey())))
                .email(userData.get(MapKeysEnum.EMAIL.getKey()))
                .password(userData.get(MapKeysEnum.PASSWORD.getKey()))
                .balance(0.00)
                .build();

        CRUDUtils.createUser(user);
        log.info("Successful registration of a new user '" + user.getEmail() + "'.");


        HttpSession session = req.getSession();
        session.setAttribute(RequestParamsEnum.USER.getValue(), user);

        req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.WELCOME_INFO.getInfo() + user.getName() + ".");

        showCategories(req);
        return PagesPathEnum.HOME_PAGE.getPath();
    }

    private void checkUserAlreadyExists(String email) throws UserAlreadyExistsException {
        User user = CRUDUtils.getUser(email);
        if (user != null) {
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует. " +
                    "Чтобы войти в аккаунт, перейдите на страницу входа...");
        }
    }
}
