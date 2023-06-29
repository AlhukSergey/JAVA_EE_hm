package by.teachmeskills.shop.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private double balance;
    private String email;
    private String password;
}
