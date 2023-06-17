package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.commands.BaseCommand;
import by.teachmeskills.shop.commands.LoginCommandImpl;
import by.teachmeskills.shop.commands.RegistrationCommandImp;
import by.teachmeskills.shop.commands.enums.CommandsEnum;
import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Map<String, BaseCommand> COMMAND_LIST = new HashMap<>();

    static {
        COMMAND_LIST.put(CommandsEnum.REGISTRATION_COMMAND.getCommand(), new RegistrationCommandImp());
        COMMAND_LIST.put(CommandsEnum.LOGIN_COMMAND.getCommand(), new LoginCommandImpl());
    }

    public static BaseCommand defineCommand (HttpServletRequest req) {
        String commandKey = req.getParameter(RequestParamsEnum.COMMAND.getValue());
        if(commandKey == null || commandKey.isEmpty()) {
            commandKey = CommandsEnum.REGISTRATION_COMMAND.getCommand();
        }
        return COMMAND_LIST.get(commandKey);
    }
}
