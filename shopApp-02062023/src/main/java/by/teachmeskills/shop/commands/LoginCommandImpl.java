package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.INFO;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.LOGIN;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.PASSWORD;
import static by.teachmeskills.shop.commands.enums.RequestParamsEnum.USER;

public class LoginCommandImpl implements BaseCommand {

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter(LOGIN.getValue());
        String password = req.getParameter(PASSWORD.getValue());
        validateCredentials(email, password);

        User user = CRUDUtils.getUser(email);

        String varInfo;
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(USER.getValue(), user);

            varInfo = "Добро пожаловать, " + user.getName() + ".";
            req.setAttribute(INFO.getValue(), varInfo);

            showCategories(req);
            return PagesPathEnum.HOME_PAGE.getPath();
        } else {
            varInfo = "Введены неверные данные. Пожалуйста, введите данные повторны либо перейдите на страницу регистрации.";
            req.setAttribute(INFO.getValue(), varInfo);
            return PagesPathEnum.LOGIN_PAGE.getPath();
        }
    }

    private void validateCredentials(String email, String password) {
        try {
            HttpRequestCredentialsValidator.validateCredential(email);
            HttpRequestCredentialsValidator.validateCredential(password);
        } catch (RequestCredentialsNullException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showCategories(HttpServletRequest req) {
        List<Category> categories = CRUDUtils.getCategories();
        req.setAttribute(CATEGORIES.getValue(), categories);
        HttpSession session = req.getSession();
        session.setAttribute(CATEGORIES.getValue(), categories);
    }
}
