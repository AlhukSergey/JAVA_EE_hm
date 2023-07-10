package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends BaseService<Order> {
    Order findById(int id);

    List<Order> findByDate(LocalDateTime date);
    List<Order> findByUserId(int id);
}
