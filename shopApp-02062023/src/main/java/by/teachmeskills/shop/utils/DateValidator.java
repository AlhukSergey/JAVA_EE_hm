package by.teachmeskills.shop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator implements Validator {

    @Override
    public boolean validate(String str) {
        String regex = "^((19[3-9][0-9])|(20[0-1][0-7]))-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
