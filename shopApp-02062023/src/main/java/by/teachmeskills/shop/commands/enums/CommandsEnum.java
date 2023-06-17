package by.teachmeskills.shop.commands.enums;

public enum CommandsEnum {
    REGISTRATION_COMMAND("registration-user"),
    LOGIN_COMMAND("login");
    private final String command;

    CommandsEnum(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
