package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.MapKeys;
import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.SetterActions;
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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class UpdateUserDataCommandImpl implements BaseCommand {
    private final static Logger log = LoggerFactory.getLogger(UpdateUserDataCommandImpl.class);
    private static final String SUCCESS_INFO = "Данные успешно изменены!";
    private static final Map<String, BiConsumer<String, User>> settersMap = Map.of(
            MapKeys.NAME.getKey(), SetterActions.NAME_ACTION.getAction(),
            MapKeys.SURNAME.getKey(), SetterActions.SURNAME_ACTION.getAction(),
            MapKeys.BIRTHDAY.getKey(), SetterActions.BIRTHDAY_ACTION.getAction(),
            MapKeys.EMAIL.getKey(), SetterActions.EMAIL_ACTION.getAction(),
            MapKeys.PASSWORD.getKey(), SetterActions.PASSWORD_ACTION.getAction());

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        try {
            if (userData.containsKey("new_password")) {
                HttpRequestCredentialsValidator.validatePasswords(userData, user);
            } else {
                HttpRequestCredentialsValidator.validateUserData(userData);
            }
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            String varInfo = e.getMessage();
            log.info(e.getMessage());
            req.setAttribute("info", varInfo);
            return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
        }

        setNewUserData(userData, user);
        CRUDUtils.updateUserData(CRUDUtils.generateUpdateStatement(userData, user.getId()));
        log.info("The data of the user '" + user.getEmail() + "' successful changed.");
        req.setAttribute("info", SUCCESS_INFO);

        PageFiller.showUserData(req);
        return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
    }

    private void setNewUserData(Map<String, String> userData, User user) {
        List<String> userFieldsNames = Arrays.stream(user.getClass().getDeclaredFields()).map(Field::getName).toList();
        Set<String> keys = userData.keySet();
        for (String name : userFieldsNames) {
            if (keys.contains(name)) {
                settersMap.get(name).accept(userData.get(name), user);
            }
        }
    }
}
