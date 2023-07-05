package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.commands.*;
import by.teachmeskills.shop.commands.enums.CommandsEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private CommandFactory() {
    }

    private static final Map<String, BaseCommand> COMMAND_LIST = new HashMap<>();

    static {
        COMMAND_LIST.put(CommandsEnum.SHOW_LOGIN_PAGE_COMMAND.getCommand(), new ShowLoginPageCommandImpl());
        COMMAND_LIST.put(CommandsEnum.REGISTRATION_USER_COMMAND.getCommand(), new RegistrationUserCommandImpl());
        COMMAND_LIST.put(CommandsEnum.LOGIN_COMMAND.getCommand(), new LoginCommandImpl());
        COMMAND_LIST.put(CommandsEnum.REDIRECT_TO_REGISTRATION_PAGE_COMMAND.getCommand(), new RedirectToRegistrationPageCommandImpl());
        COMMAND_LIST.put(CommandsEnum.REDIRECT_TO_CATEGORY_PAGE_COMMAND.getCommand(), new RedirectToCategoryPageImpl());
        COMMAND_LIST.put(CommandsEnum.REDIRECT_TO_PRODUCT_PAGE_COMMAND.getCommand(), new RedirectToProductPageCommandImpl());
        COMMAND_LIST.put(CommandsEnum.REDIRECT_TO_SHOPPING_CART_PAGE_COMMAND.getCommand(), new RedirectToShoppingCartPageCommandImpl());
        COMMAND_LIST.put(CommandsEnum.REDIRECT_TO_HOME_PAGE_COMMAND.getCommand(), new RedirectToHomePageCommandIml());
        COMMAND_LIST.put(CommandsEnum.REDIRECT_TO_MY_PAGE_COMMAND.getCommand(), new RedirectToMyPageCommandImpl());
        COMMAND_LIST.put(CommandsEnum.ADD_PRODUCT_TO_SHOPPING_CART_COMMAND.getCommand(), new AddProductToShoppingCartCommandImpl());
        COMMAND_LIST.put(CommandsEnum.REMOVE_PRODUCT_FROM_SHOPPING_CART_COMMAND.getCommand(), new RemoveProductFromShoppingCartCommandImpl());
        COMMAND_LIST.put(CommandsEnum.REDIRECT_TO_USER_ACCOUNT_PAGE_COMMAND.getCommand(), new RedirectToUserAccountPageImpl());
        COMMAND_LIST.put(CommandsEnum.CHECKOUT_COMMAND.getCommand(), new CheckoutCommandImpl());
        COMMAND_LIST.put(CommandsEnum.UPDATE_USER_DATA_COMMAND.getCommand(), new UpdateUserDataCommandImpl());
    }

    public static BaseCommand defineCommand(HttpServletRequest req) {
        String commandKey = req.getParameter(RequestParamsEnum.COMMAND.getValue());
        if (commandKey == null || commandKey.isEmpty()) {
            commandKey = CommandsEnum.SHOW_LOGIN_PAGE_COMMAND.getCommand();
        }
        return COMMAND_LIST.get(commandKey);
    }
}
