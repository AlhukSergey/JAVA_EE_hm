package by.teachmeskills.ps.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Merchant {
    private String id;
    private String name;
    private List<BankAccount> bankAccounts;
    private LocalDateTime createdAt;

    public Merchant(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        bankAccounts = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public Merchant(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String toString() {
        return id + " " + name + " " + createdAt;
    }
}
