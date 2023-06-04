package by.teachmeskills.shop.model;

import java.util.UUID;

public class User {
    private final String id;
    private String name;
    private String surname;
    private double balance;
    private String email;
    private String password;

    public User(String name, String surname, String email, String password) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        balance = 0;
        this.email = email;
        this.password = password;
    }

    public User(String id, String name, String surname, double balance, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
        this.email = email;
        this.password = password;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
