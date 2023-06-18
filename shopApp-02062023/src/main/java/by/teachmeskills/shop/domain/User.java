package by.teachmeskills.shop.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private final String name;
    private final String surname;
    private final LocalDate birthday;
    private final double balance;
    private final String email;
    private final String password;
}
