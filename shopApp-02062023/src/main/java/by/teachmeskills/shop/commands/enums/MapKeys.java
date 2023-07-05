package by.teachmeskills.shop.commands.enums;

public enum MapKeys {
    NAME("name"),
    SURNAME("surname"),
    BIRTHDAY("birthday"),
    EMAIL("email"),
    PASSWORD("new_password");

    private final String key;

    MapKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
