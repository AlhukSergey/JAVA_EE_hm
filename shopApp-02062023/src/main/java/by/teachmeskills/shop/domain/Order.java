package by.teachmeskills.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private int id;
    private int userId;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private List<Product> productList;
    private double price;

    public Order(User user, List<Product> productList, double price) {
        this.userId = user.getId();
        orderStatus = OrderStatus.ACTIVE;
        createdAt = LocalDateTime.now();
        this.productList = productList;
        this.price = price;
    }

    public Order(int id, int userId, LocalDateTime createdAt, OrderStatus orderStatus, double price) {
        this.id =id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.price = price;
    }

}
