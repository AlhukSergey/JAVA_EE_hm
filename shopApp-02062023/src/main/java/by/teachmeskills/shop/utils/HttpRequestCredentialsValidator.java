package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.exceptions.AgeLimitException;
import by.teachmeskills.shop.exceptions.InvalidDateFormatException;
import by.teachmeskills.shop.exceptions.InvalidEmailException;
import by.teachmeskills.shop.exceptions.InvalidPasswordException;
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

    public static boolean validateCredentials(Map<String, String> credentials) throws InvalidDateFormatException, AgeLimitException, InvalidEmailException, InvalidPasswordException, RequestCredentialsNullException {
        //if all validations true, the method will return "true".
        //if any validation returns "false", the method will throw an exception and notify the user of an input error.
        boolean result = true;

        // check all data for empty or null
        Predicate<String> isNotNull = Objects::nonNull;
        Predicate<String> isNotEmpty = line -> !line.isEmpty();
        Predicate<String> isNotNullAndEmpty = isNotNull.and(isNotEmpty);
        for (Map.Entry<String, String> item : credentials.entrySet()) {
            if(!isNotNullAndEmpty.test(item.getValue())) {
                throw new RequestCredentialsNullException("Учетные данные запроса не инициализированы!");
            }
        }

        // check date format
        Validator dateFormatValidator = new DateFormatValidator();
        if (!dateFormatValidator.validate(credentials.get("BIRTHDAY"))) {
            throw new InvalidDateFormatException("Неверный формат даты!");
        }

        //checking that user is at least 16 years old, not older 73.
        Validator dateValidator = new DateValidator();
        if (!dateValidator.validate(credentials.get("BIRTHDAY"))) {
            throw new AgeLimitException("Введен неверный возраст. Возраст не может быть меньше 16 и больше 73 лет!");
        }

        //check email
        Validator emailValidator = new EmailValidator();
        if (!emailValidator.validate(credentials.get("EMAIL"))) {
            throw new InvalidEmailException("Неверный адрес электронной почты!");
        }


        //check password
        Validator passValidator = new PassValidator();
        if (!passValidator.validate(credentials.get("PASSWORD"))) {
            throw new InvalidPasswordException("Неверный пароль!");
        }

        return result;
    }
}
