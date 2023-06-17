package by.teachmeskills.shop.commands.enums;

public enum RequestParamsEnum {
    COMMAND("command"),
    LOGIN("email"),
    PASSWORD("password");

    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
