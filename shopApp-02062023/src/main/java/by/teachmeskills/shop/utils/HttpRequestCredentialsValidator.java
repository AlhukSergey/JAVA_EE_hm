package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class HttpRequestCredentialsValidator {
    private HttpRequestCredentialsValidator() {
    }

    public static void validateCredential(String credential) throws RequestCredentialsNullException {
        if (credential == null) {
            throw new RequestCredentialsNullException("Request credential is not initialized!");
        }
    }

    public static boolean validateCredentials(Map<String, String> credentials) {
        //if all validations true, the method will return "true".
        //if any validation returns "false", the method will return "false".
        boolean result = true;

        // check all data for empty or null
        Predicate<String> isNotNull = Objects::nonNull;
        Predicate<String> isNotEmpty = line -> !line.isEmpty();
        Predicate<String> isNotNullAndEmpty = isNotNull.and(isNotEmpty);
        for (Map.Entry<String, String> item : credentials.entrySet()) {
            isNotNullAndEmpty.test(item.getValue());
        }

        // check date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(credentials.get("BIRTHDAY"));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            result = false;
        }

        //check email
        if (!((credentials.get("EMAIL").contains("@gmail.com") || credentials.get("EMAIL").contains("@mail.ru")))) {
            result = false;
        }

        //check password
        if (credentials.get("PASSWORD").length() < 4) {
            result = false;
        }

        return result;
    }
}
