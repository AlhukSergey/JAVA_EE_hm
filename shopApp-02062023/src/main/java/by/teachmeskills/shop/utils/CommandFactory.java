package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.commands.AddProductToShoppingCartCommandImpl;
import by.teachmeskills.shop.commands.BaseCommand;
import by.teachmeskills.shop.commands.LoginCommandImpl;
import by.teachmeskills.shop.commands.RedirectToCategoryPageImpl;
import by.teachmeskills.shop.commands.RedirectToHomePageCommandIml;
import by.teachmeskills.shop.commands.RedirectToMyPageCommandImpl;
import by.teachmeskills.shop.commands.RedirectToProductPageCommandImpl;
import by.teachmeskills.shop.commands.RedirectToRegistrationPageCommandImpl;
import by.teachmeskills.shop.commands.RedirectToShoppingCartPageCommandImpl;
import by.teachmeskills.shop.commands.RegistrationUserCommandImpl;
import by.teachmeskills.shop.commands.RemoveProductFromShoppingCartCommandImpl;
import by.teachmeskills.shop.commands.ShowLoginPageCommandImpl;
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
    }

    public static BaseCommand defineCommand(HttpServletRequest req) {
        String commandKey = req.getParameter(RequestParamsEnum.COMMAND.getValue());
        if (commandKey == null || commandKey.isEmpty()) {
            commandKey = CommandsEnum.SHOW_LOGIN_PAGE_COMMAND.getCommand();
        }
        return COMMAND_LIST.get(commandKey);
    }
}
