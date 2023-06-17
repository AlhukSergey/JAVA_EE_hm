package by.teachmeskills.shop.utils;

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
        Predicate<String> isNotNull = Objects::nonNull;
        Predicate<String> isNotEmpty = line -> !line.isEmpty();
        Predicate<String> isNotNullAndEmpty = isNotNull.and(isNotEmpty);
        for (Map.Entry<String, String> item : credentials.entrySet()) {
            if (!isNotNullAndEmpty.test(item.getValue())) {
                throw new RequestCredentialsNullException("Учетные данные запроса не инициализированы!");
            }
        }

        // check date format
        if (!DataValidator.validateDateFormat(credentials.get("BIRTHDAY"))) {
            throw new IncorrectUserDataException("Неверный формат даты!");
        }

        //checking that user is at least 16 years old, not older 73.
        if (!DataValidator.validateDate(credentials.get("BIRTHDAY"))) {
            throw new IncorrectUserDataException("Введен неверный возраст. Возраст не может быть меньше 16 и больше 73 лет!");
        }

        //check email
        if (!DataValidator.validateEmail(credentials.get("EMAIL"))) {
            throw new IncorrectUserDataException("Неверный формат адреса электронной почты!");
        }

        //check password
        if (!DataValidator.validatePassword(credentials.get("PASSWORD"))) {
            throw new IncorrectUserDataException("Неверный формат пароля! " +
                    "Длина пароля должна быть не короче 8 символов. Пароль должен содержать как минимум одну цифру," +
                    "одну заглавную букву, одну букву нижнего регистра, один специальный символ.");


        }
    }
}
