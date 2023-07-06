package by.teachmeskills.shop.commands.enums;

import by.teachmeskills.shop.utils.EncryptionUtils;

import java.util.function.BiConsumer;

public enum StatementActions {
    NAME_ACTION((name, stringBuilder) -> stringBuilder.append(UsersTableColumnNames.NAME.getRowName()).append(" = '").append(name)),
    SURNAME_ACTION((surname, stringBuilder) -> stringBuilder.append(UsersTableColumnNames.SURNAME.getRowName()).append(" = '").append(surname)),
    BIRTHDAY_ACTION((birthday, stringBuilder) -> stringBuilder.append(UsersTableColumnNames.BIRTHDAY.getRowName()).append(" = '").append(birthday)),
    EMAIL_ACTION((email, stringBuilder) -> stringBuilder.append(UsersTableColumnNames.EMAIL.getRowName()).append(" = '").append(email)),
    PASSWORD_ACTION((password, stringBuilder) -> stringBuilder.append(UsersTableColumnNames.PASSWORD.getRowName()).append(" = '").append(EncryptionUtils.encrypt(password)));
    private final BiConsumer<String, StringBuilder> action;

    StatementActions(BiConsumer<String, StringBuilder> action) {
        this.action = action;
    }

    public BiConsumer<String, StringBuilder> getAction() {
        return action;
    }
}
