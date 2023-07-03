package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.utils.CRUDUtils;
import by.teachmeskills.shop.utils.HttpRequestCredentialsValidator;
import by.teachmeskills.shop.utils.PageFiller;
import by.teachmeskills.shop.utils.RequestDataGetter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Map;

public class UpdateUserDataCommandImpl implements BaseCommand {
    private final static Logger log = LoggerFactory.getLogger(UpdateUserDataCommandImpl.class);
    private static final String SUCCESS_INFO = "Данные успешно изменены!";

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        try {
            HttpRequestCredentialsValidator.validateUserData(userData);
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            String varInfo = e.getMessage();
            log.info("Wrong password entered. " + user.getEmail() + ".");
            req.setAttribute("info", varInfo);
            return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
        }

        user.setName(userData.get("NAME"));
        user.setSurname(userData.get("SURNAME"));
        user.setBirthday(LocalDate.parse(userData.get("BIRTHDAY")));
        user.setEmail(userData.get("EMAIL"));
        CRUDUtils.updateUserData(user);

        log.info("The data of the user '" + user.getEmail() + "' successful changed.");
        req.setAttribute("info", SUCCESS_INFO);

        PageFiller.showUserData(req);
        return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
    }
}
