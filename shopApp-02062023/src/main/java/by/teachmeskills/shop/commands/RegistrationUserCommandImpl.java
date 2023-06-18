package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.DateParser;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationUserCommandImpl implements BaseCommand {
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
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            String varInfo = "Введены некорректные  данные. " + e.getMessage();
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


        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        String varInfo = "Добро пожаловать, " + user.getName() + ".";
        req.setAttribute("info", varInfo);

        showCategories(req);
        return PagesPathEnum.HOME_PAGE.getPath();
    }

    private void showCategories(HttpServletRequest req) {
        List<Category> categories = CRUDUtils.getCategories();
        req.setAttribute("categories", categories);
        HttpSession session = req.getSession();
        session.setAttribute(RequestParamsEnum.CATEGORIES.getValue(), categories);
    }
}
