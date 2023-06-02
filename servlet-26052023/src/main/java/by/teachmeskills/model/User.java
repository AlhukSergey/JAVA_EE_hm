package by.teachmeskills.model;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final String ID;
    private String name;
    private int age;
    private String email;
    private String password;

    public User(String name, int age, String email, String password) {
        ID = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public User(String ID, String name, int age, String email, String password) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
