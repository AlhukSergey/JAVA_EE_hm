package by.teachmeskills.shop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatValidator implements Validator{
    @Override
    public boolean validate(String str) {
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
}
