package by.teachmeskills.shop.commands.enums;

public enum UsersTableRowName {
    ID("id"),
    NAME("name"),
    SURNAME("surname"),
    BIRTHDAY("birthday"),
    BALANCE("balance"),
    EMAIL("email"),
    PASSWORD("password");

    private final String rowName;

    UsersTableRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getRowName() {
        return rowName;
    }
}
