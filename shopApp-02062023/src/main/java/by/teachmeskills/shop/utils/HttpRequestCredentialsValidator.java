package by.teachmeskills.shop.utils;

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

        if (credentials.containsKey("birthday")) {
            // check date format
            if (!DataValidator.validateDateFormat(credentials.get("birthday"))) {
                throw new IncorrectUserDataException("Неверный формат даты!");
            }

            //checking that user is at least 16 years old, not older 73.
            if (!DataValidator.validateDate(credentials.get("birthday"))) {
                throw new IncorrectUserDataException("Введен неверный возраст. Возраст не может быть меньше 16 и больше 73 лет!");
            }
        }

        //check email
        if (credentials.containsKey("email")){
            if (!DataValidator.validateEmail(credentials.get("email"))) {
                throw new IncorrectUserDataException("Неверный формат адреса электронной почты!");
            }
        }

        //check password
        if (credentials.containsKey("password")) {
            if (!DataValidator.validatePassword(credentials.get("password"))) {
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
        if (!user.getPassword().equals(passwords.get("old_password"))) {
            throw new IncorrectUserDataException("Введен неверный действующий пароль. Пожалуйста, повторите попытку.");
        }

        //check password
        if (!DataValidator.validatePassword(passwords.get("new_password"))) {
            throw new IncorrectUserDataException("Неверный формат пароля! " +
                    "Длина пароля должна быть не короче 8 символов. Пароль должен содержать как минимум одну цифру," +
                    "одну заглавную букву, одну букву нижнего регистра, один специальный символ.");
        }

        if (!passwords.get("new_password_rep").equals(passwords.get("new_password"))) {
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
