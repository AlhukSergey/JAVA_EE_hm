package by.teachmeskills.shop.repositories;

import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    Order findById(int id);

    List<Order> findByDate(LocalDateTime date);
    List<Order> getOrdersByUserIdAndStatus(int Id, OrderStatus status);
}
