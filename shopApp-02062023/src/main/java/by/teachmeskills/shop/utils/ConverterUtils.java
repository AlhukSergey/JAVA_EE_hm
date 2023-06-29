package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.domain.OrderStatus;

public class ConverterUtils {
    private ConverterUtils() {
    }

    public static OrderStatus toOrderStatus(String status) {
        return status.equalsIgnoreCase("ACTIVE") ? OrderStatus.ACTIVE : OrderStatus.FINISHED;
    }
}
