package by.teachmeskills.ps.utils;

import by.teachmeskills.ps.model.AccountStatus;

public class ConverterUtils {
    private ConverterUtils() {
    }

    public static AccountStatus toAccountStatus(String status) {
        return status.equalsIgnoreCase("ACTIVE") ? AccountStatus.ACTIVE : AccountStatus.DELETED;
    }
}
