package by.teachmeskills.shop.commands;

import by.teachmeskills.shop.commands.enums.InfoEnum;
import by.teachmeskills.shop.commands.enums.MapKeysEnum;
import by.teachmeskills.shop.commands.enums.PagesPathEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import by.teachmeskills.shop.commands.enums.SetterActionsEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.CommandException;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;
import by.teachmeskills.shop.services.Impl.OrderServiceImpl;
import by.teachmeskills.shop.services.Impl.UserServiceImpl;
import by.teachmeskills.shop.services.OrderService;
import by.teachmeskills.shop.services.UserService;
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
    private final UserService userService = new UserServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final static Logger log = LoggerFactory.getLogger(UpdateUserDataCommandImpl.class);
    private static final Map<String, BiConsumer<String, User>> settersMap = Map.of(
            MapKeysEnum.NAME.getKey(), SetterActionsEnum.NAME_ACTION.getAction(),
            MapKeysEnum.SURNAME.getKey(), SetterActionsEnum.SURNAME_ACTION.getAction(),
            MapKeysEnum.BIRTHDAY.getKey(), SetterActionsEnum.BIRTHDAY_ACTION.getAction(),
            MapKeysEnum.EMAIL.getKey(), SetterActionsEnum.EMAIL_ACTION.getAction(),
            MapKeysEnum.NEW_PASSWORD.getKey(), SetterActionsEnum.PASSWORD_ACTION.getAction());

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        Map<String, String> userData = RequestDataGetter.getData(req);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(RequestParamsEnum.USER.getValue());

        try {
            if (userData.containsKey(MapKeysEnum.NEW_PASSWORD.getKey())) {
                HttpRequestCredentialsValidator.validatePasswords(userData, user);
            } else {
                HttpRequestCredentialsValidator.validateUserData(userData);
            }
        } catch (IncorrectUserDataException | RequestCredentialsNullException e) {
            log.info(e.getMessage());
            req.setAttribute(RequestParamsEnum.INFO.getValue(), e.getMessage());
            return PagesPathEnum.USER_ACCOUNT_PAGE.getPath();
        }

        setNewUserData(userData, user);

        userService.generateForUpdate(userData, user.getId());
        userService.update(user);

        log.info(InfoEnum.DATA_SUCCESSFUL_CHANGED_INFO.getInfo() + user.getEmail() + "'.");

        req.setAttribute(RequestParamsEnum.INFO.getValue(), InfoEnum.DATA_SUCCESSFUL_CHANGED_INFO.getInfo());

        PageFiller.showUserData(req, orderService, user);
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
