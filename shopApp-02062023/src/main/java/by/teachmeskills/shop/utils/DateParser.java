package by.teachmeskills.shop.utils;

import java.time.LocalDate;

public class DateParser {
    private DateParser() {
    }

    public static LocalDate parseToDate(String data) {
        return LocalDate.parse(data);
    }
}
