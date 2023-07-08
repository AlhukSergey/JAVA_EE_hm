package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.commands.enums.MapKeysEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class HttpRequestCredentialsValidator {
    private HttpRequestCredentialsValidator() {
    }

    public static void validateCredential(String credential) throws RequestCredentialsNullException {
        if (credential == null) {
            throw new RequestCredentialsNullException("Учетные данные запроса не инициализированы!");
        }
    }

    public static void validateUserData(Map<String, String> credentials) throws IncorrectUserDataException, RequestCredentialsNullException {
        // check all data for empty or null
        checkNotNullAndNotEmpty(credentials);

        if (credentials.containsKey(MapKeysEnum.BIRTHDAY.getKey())) {
            // check date format
            if (!DataValidator.validateDateFormat(credentials.get(MapKeysEnum.BIRTHDAY.getKey()))) {
                throw new IncorrectUserDataException("Неверный формат даты!");
            }

            //checking that user is at least 16 years old, not older 73.
            if (!DataValidator.validateDate(credentials.get(MapKeysEnum.BIRTHDAY.getKey()))) {
                throw new IncorrectUserDataException("Введен неверный возраст. Возраст не может быть меньше 16 и больше 73 лет!");
            }
        }

        //check email
        if (credentials.containsKey(MapKeysEnum.EMAIL.getKey())){
            if (!DataValidator.validateEmail(credentials.get(MapKeysEnum.EMAIL.getKey()))) {
                throw new IncorrectUserDataException("Неверный формат адреса электронной почты!");
            }
        }

        //check password
        if (credentials.containsKey(MapKeysEnum.PASSWORD.getKey())) {
            if (DataValidator.validatePassword(credentials.get(MapKeysEnum.PASSWORD.getKey()))) {
                throw new IncorrectUserDataException("Неверный формат пароля! " +
                        "Длина пароля должна быть не короче 8 символов. Пароль должен содержать как минимум одну цифру," +
                        "одну заглавную букву, одну букву нижнего регистра, один специальный символ.");
            }
        }
    }

    public static void validatePasswords(Map<String, String> passwords, User user) throws RequestCredentialsNullException, IncorrectUserDataException {
        // check all data for empty or null
        checkNotNullAndNotEmpty(passwords);

        //check old password is correct
        if (!user.getPassword().equals(passwords.get(MapKeysEnum.OLD_PASSWORD.getKey()))) {
            throw new IncorrectUserDataException("Введен неверный действующий пароль. Пожалуйста, повторите попытку.");
        }

        //check password
        if (DataValidator.validatePassword(passwords.get(MapKeysEnum.NEW_PASSWORD.getKey()))) {
            throw new IncorrectUserDataException("Неверный формат пароля! " +
                    "Длина пароля должна быть не короче 8 символов. Пароль должен содержать как минимум одну цифру," +
                    "одну заглавную букву, одну букву нижнего регистра, один специальный символ.");
        }

        if (!passwords.get(MapKeysEnum.NEW_PASSWORD_REP.getKey()).equals(passwords.get(MapKeysEnum.NEW_PASSWORD.getKey()))) {
            throw new IncorrectUserDataException("Пароли не совпадают.");
        }
    }

    private static void checkNotNullAndNotEmpty(Map<String, String> requestItems) throws RequestCredentialsNullException {
        // check all data for empty or null
        Predicate<String> isNotNull = Objects::nonNull;
        Predicate<String> isNotEmpty = line -> !line.isEmpty();
        Predicate<String> isNotNullAndEmpty = isNotNull.and(isNotEmpty);
        for (Map.Entry<String, String> item : requestItems.entrySet()) {
            if (!isNotNullAndEmpty.test(item.getValue())) {
                throw new RequestCredentialsNullException("Учетные данные запроса не инициализированы!");
            }
        }
    }
}
