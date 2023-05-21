package by.teachmeskills.ps.utils;

import java.util.Base64;

public class EncryptionUtils {
    private EncryptionUtils() {
    }

    public static String encrypt(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decrypt(String str) {
        return new String(Base64.getDecoder().decode(str));
    }
}
