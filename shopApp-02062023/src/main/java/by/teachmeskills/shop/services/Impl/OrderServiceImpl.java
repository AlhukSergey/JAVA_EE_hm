package by.teachmeskills.shop.services.Impl;

import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.repositories.Impl.OrderRepositoryImpl;
import by.teachmeskills.shop.repositories.OrderRepository;
import by.teachmeskills.shop.services.OrderService;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    @Override
    public Order create(Order entity) {
        return orderRepository.create(entity);
    }

    @Override
    public List<Order> read() {
        return orderRepository.read();
    }

    @Override
    public Order update(Order entity) {
        return orderRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        orderRepository.delete(id);
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByDate(LocalDateTime date) {
        return orderRepository.findByDate(date);
    }
}
