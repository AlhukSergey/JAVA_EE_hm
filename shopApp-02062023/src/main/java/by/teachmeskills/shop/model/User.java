package by.teachmeskills.shop.model;

import java.time.LocalDate;
import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private final String surname;
    private final LocalDate birthday;
    private final double balance;
    private final String email;
    private final String password;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.birthday = builder.birthday;
        this.balance = builder.balance;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String surname;
        private LocalDate birthday;
        private double balance;
        private String email;
        private String password;

        private Builder(){}

        public Builder withId() {
            this.id = UUID.randomUUID().toString();
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }
        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}
