package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.model.Category;
import by.teachmeskills.shop.model.User;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class LoginCommandImpl implements BaseCommand {

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        String email = req.getParameter(RequestParamsEnum.LOGIN.getValue());
        String password = req.getParameter(RequestParamsEnum.PASSWORD.getValue());
        validateCredentials(email, password);

        User user = CRUDUtils.getUser(email);

        String varInfo;
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            varInfo = "Добро пожаловать, " + user.getName() + ".";
            req.setAttribute("info", varInfo);

            showCategories(req);
            return PagesPathEnum.HOME_PAGE.getPath();
        } else {
            varInfo = "Введены неверные данные. Пожалуйста, введите данные повторны либо перейдите на страницу регистрации.";
            req.setAttribute("info", varInfo);
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
        req.setAttribute("categories", categories);
    }
}
