package by.teachmeskills.shop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassValidator implements Validator {
    /*
        (?=.*[0-9]) a digit must occur at least once
        (?=.*[a-z]) a lower case letter must occur at least once
        (?=.*[A-Z]) an upper case letter must occur at least once
        (?=.*[@#$%^&+=]) a special character must occur at least once
        (?=\\S+$) no whitespace allowed in the entire string
        .{8,} at least 8 characters
        */
    private static final String PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final Pattern PATTERN = Pattern.compile(PASS_PATTERN);

    @Override
    public boolean validate(String password) {
        Matcher matcher = PATTERN.matcher(password);
        return matcher.matches();
    }
}
