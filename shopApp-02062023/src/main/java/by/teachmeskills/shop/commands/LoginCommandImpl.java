package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import by.teachmeskills.shop.utils.RequestDataGetter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static by.teachmeskills.shop.utils.PageFiller.showCategories;

public class LoginCommandImpl implements BaseCommand {
    private final static Logger log = LoggerFactory.getLogger(LoginCommandImpl.class);
    private final String WELCOME_INFO = "Добро пожаловать, ";
    private final String USER_NOT_FOUND_INFO = "Пользователя с таким логином не существует. Пожалуйста, введите данные повторно либо перейдите на страницу регистрации.";
    private final String PASSWORD_INCORRECT_INFO = "Введен неверный пароль. Повторите попытку.";

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        validateCredentials(userData.get("EMAIL"), userData.get("PASSWORD"));

        User user = CRUDUtils.getUser(userData.get("EMAIL"));

        String varInfo;
        if (user == null) {
            varInfo = USER_NOT_FOUND_INFO;
            req.setAttribute(RequestParamsEnum.INFO.getValue(), varInfo);
            return PagesPathEnum.LOGIN_PAGE.getPath();
        }

        if (!user.getPassword().equals(userData.get("PASSWORD"))) {
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

        showCategories(req);
        return PagesPathEnum.HOME_PAGE.getPath();
    }

    private void validateCredentials(String email, String password) {
        try {
            HttpRequestCredentialsValidator.validateCredential(email);
            HttpRequestCredentialsValidator.validateCredential(password);
        } catch (RequestCredentialsNullException e) {
            System.out.println(e.getMessage());
        }
    }
}
