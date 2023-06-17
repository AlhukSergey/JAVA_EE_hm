package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.model.Category;
import by.teachmeskills.shop.model.User;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.DateParser;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationCommandImp implements BaseCommand {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = new HashMap<>();
        userData.put("NAME", req.getParameter("name"));
        userData.put("SURNAME", req.getParameter("surname"));
        userData.put("BIRTHDAY", req.getParameter("birthday"));
        userData.put("EMAIL", req.getParameter("email"));
        userData.put("PASSWORD", req.getParameter("password"));

        try {
            HttpRequestCredentialsValidator.validateUserData(userData);
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            String varInfo = "Введены некорректные  данные. " + e.getMessage();
            req.setAttribute("info", varInfo);
            return PagesPathEnum.REGISTRATION_PAGE.getPath();
        }

        User user = User.newBuilder()
                .withId()
                .withName(userData.get("NAME"))
                .withSurname(userData.get("SURNAME"))
                .withBirthday(DateParser.parseToDate(userData.get("BIRTHDAY")))
                .withEmail(userData.get("EMAIL"))
                .withPassword(userData.get("PASSWORD"))
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
    }
}
