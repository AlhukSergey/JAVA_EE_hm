package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.exceptions.RequestCredentialsNullException;

public class HttpRequestCredentialsValidator {
    private HttpRequestCredentialsValidator() {
    }

    public static void validateCredentialNotNull(String credential) throws RequestCredentialsNullException {
        if (credential == null) {
            throw new RequestCredentialsNullException("Request credential is not initialized!");
        }
    }
}
