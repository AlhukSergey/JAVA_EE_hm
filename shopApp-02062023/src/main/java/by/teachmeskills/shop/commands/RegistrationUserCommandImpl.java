package by.teachmeskills.shop.commands;

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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static by.teachmeskills.shop.utils.HomePageFiller.showCategories;

public class RegistrationUserCommandImpl implements BaseCommand {
    private final static Logger log = LoggerFactory.getLogger(RegistrationUserCommandImpl.class);
    private final String WELCOME_INFO = "Добро пожаловать, ";
    private final String ERROR_DATA_INFO = "Введены некорректные  данные. ";

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = new HashMap<>();
        userData.put("NAME", req.getParameter(RequestParamsEnum.NAME.getValue()));
        userData.put("SURNAME", req.getParameter(RequestParamsEnum.SURNAME.getValue()));
        userData.put("BIRTHDAY", req.getParameter(RequestParamsEnum.BIRTHDAY.getValue()));
        userData.put("EMAIL", req.getParameter(RequestParamsEnum.EMAIL.getValue()));
        userData.put("PASSWORD", req.getParameter(RequestParamsEnum.PASSWORD.getValue()));

        try {
            HttpRequestCredentialsValidator.validateUserData(userData);
            checkUserAlreadyExists(userData.get("EMAIL"));
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            String varInfo = ERROR_DATA_INFO + e.getMessage();
            req.setAttribute("info", varInfo);
            return PagesPathEnum.REGISTRATION_PAGE.getPath();
        } catch (UserAlreadyExistsException e) {
            String varInfo = e.getMessage();
            req.setAttribute("info", varInfo);
            return PagesPathEnum.REGISTRATION_PAGE.getPath();
        }

        User user = User.builder()
                .name(userData.get("NAME"))
                .surname(userData.get("SURNAME"))
                .birthday(DateParser.parseToDate(userData.get("BIRTHDAY")))
                .email(userData.get("EMAIL"))
                .password(userData.get("PASSWORD"))
                .balance(0.00)
                .build();

        CRUDUtils.createUser(user);
        log.info("Successful registration of a new user '" + user.getEmail() + "'.");


        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        String varInfo = WELCOME_INFO + user.getName() + ".";
        req.setAttribute("info", varInfo);

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
