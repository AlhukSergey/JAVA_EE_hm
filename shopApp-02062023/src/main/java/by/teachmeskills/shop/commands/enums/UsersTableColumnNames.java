package by.teachmeskills.shop.commands.enums;

public enum UsersTableColumnNames {
    ID("id"),
    NAME("name"),
    SURNAME("surname"),
    BIRTHDAY("birthday"),
    BALANCE("balance"),
    EMAIL("email"),
    PASSWORD("password");

    private final String rowName;

    UsersTableColumnNames(String rowName) {
        this.rowName = rowName;
    }

    public String getRowName() {
        return rowName;
    }
}
