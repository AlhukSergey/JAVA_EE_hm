package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.OrderStatus;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.repositories.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private static int lastOrderId;
    private static final String ADD_ORDER_QUERY = "INSERT INTO orders (user_id, created_at, status, price) VALUES (?, ?, ?, ?)";
    private static final String ADD_ORDER_LIST_QUERY = "INSERT INTO order_lists (orderId, productId) VALUES (?, ?)";
    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM orders";
    private static final String DELETE_ORDER_QUERY = "UPDATE orders SET status = ? WHERE id = ?";
    private static final String GET_LAST_ORDER_ID_QUERY = "SELECT MAX(id) FROM orders";
    private static final String GET_ORDERS_BY_USER_ID_AND_STATUS_QUERY = "SELECT * FROM orders WHERE user_id = ? AND status = ?";

    @Override
    public Order create(Order entity) {
        log.info("Trying to add the new order to the database.");
        try (Connection connection = pool.getConnection();
             PreparedStatement psInsert = connection.prepareStatement(ADD_ORDER_QUERY)) {
            psInsert.setInt(1, entity.getUserId());
            psInsert.setTimestamp(2, Timestamp.valueOf(entity.getCreatedAt()));
            psInsert.setString(3, OrderStatus.ACTIVE.toString());
            psInsert.setDouble(4, entity.getPrice());
            psInsert.execute();

            addOrderList(entity);

            psInsert.execute();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<Order> read() {
        log.info("Trying to get all products from the database.");

        List<Order> orders = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_ALL_ORDERS_QUERY)) {
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(LocalDateTime.parse(resultSet.getString(3)))
                        .orderStatus(OrderStatus.valueOf(resultSet.getString(4)))
                        .price(resultSet.getDouble(5))
                        .build()
                );
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return orders;
    }

    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public void delete(int id) {
        log.info("Trying to delete the order from the database.");
        try (Connection connection = pool.getConnection();
             PreparedStatement psDelete = connection.prepareStatement(DELETE_ORDER_QUERY)) {
            psDelete.setInt(1, id);
            psDelete.setString(2, String.valueOf(OrderStatus.FINISHED));
            psDelete.execute();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findByDate(LocalDateTime date) {
        return null;
    }

    private static void addOrderList(Order order) {
        getLastOrderId();
        int orderId = ++lastOrderId;

        List<Product> orderList = order.getProductList();
        for (Product product : orderList) {
            try (Connection connection = pool.getConnection();
                 PreparedStatement psInsert = connection.prepareStatement(ADD_ORDER_LIST_QUERY)) {
                psInsert.setInt(1, orderId);
                psInsert.setInt(2, product.getId());
                psInsert.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void getLastOrderId() {
        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_LAST_ORDER_ID_QUERY)) {
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                lastOrderId = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Order> getOrdersByUserIdAndStatus(int userId, OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_ORDERS_BY_USER_ID_AND_STATUS_QUERY)) {
            psGet.setInt(1, userId);
            psGet.setString(2, String.valueOf(status));
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(LocalDateTime.parse(resultSet.getString(3)))
                        .orderStatus(OrderStatus.valueOf(resultSet.getString(4)))
                        .price(resultSet.getDouble(5))
                        .build()
                );
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return orders;
    }
}
