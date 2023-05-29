package by.teachmeskills.shop.utils;

import java.util.Base64;

public class EncryptionUtils {
    private EncryptionUtils() {
    }

    public static String encrypt(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decrypt(String password) {
        return new String(Base64.getDecoder().decode(password));
    }
}