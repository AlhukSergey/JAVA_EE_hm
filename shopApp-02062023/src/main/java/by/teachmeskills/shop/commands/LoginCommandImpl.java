package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.teachmeskills.shop.utils.HomePageFiller.showCategories;

public class LoginCommandImpl implements BaseCommand {
    private final String WELCOME_INFO = "Добро пожаловать, ";
    private final String ERROR_INFO = "Пользователя с таким логином не существует. Пожалуйста, введите данные повторно либо перейдите на страницу регистрации.";

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        String email = req.getParameter(RequestParamsEnum.LOGIN.getValue());
        String password = req.getParameter(RequestParamsEnum.PASSWORD.getValue());
        validateCredentials(email, password);

        User user = CRUDUtils.getUser(email);

        String varInfo;
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(RequestParamsEnum.USER.getValue(), user);

            varInfo = WELCOME_INFO + user.getName() + ".";
            req.setAttribute(RequestParamsEnum.INFO.getValue(), varInfo);

            showCategories(req);
            return PagesPathEnum.HOME_PAGE.getPath();
        } else {
            varInfo = ERROR_INFO;
            req.setAttribute(RequestParamsEnum.INFO.getValue(), varInfo);
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
}
