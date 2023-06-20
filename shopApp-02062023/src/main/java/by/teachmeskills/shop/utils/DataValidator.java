package by.teachmeskills.shop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {
    private DataValidator() {
    }

    //check date format
    public static boolean validateDateFormat(String str) {
        boolean result = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(str);
            result = true;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //check age limit
    public static boolean validateDate(String str) {
        String regex = "^((19[3-9][0-9])|(20[0-1][0-7]))-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    //checkEmailFormat
    //Email example (used for login) : anna18@gmail.com
    public static boolean validateEmail(String email) {
        String regex = "^(.+)@(\\S+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /*
       (?=.*[0-9]) a digit must occur at least once
       (?=.*[a-z]) a lower case letter must occur at least once
       (?=.*[A-Z]) an upper case letter must occur at least once
       (?=.*[@#$%^&+=]) a special character must occur at least once
       (?=\\S+$) no whitespace allowed in the entire string
       .{8,} at least 8 characters
       Password example (used for login) : A!1+=asasasaas
       */
    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
