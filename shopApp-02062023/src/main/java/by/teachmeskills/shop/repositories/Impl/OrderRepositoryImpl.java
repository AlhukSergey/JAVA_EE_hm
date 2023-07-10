package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.OrderStatus;
import by.teachmeskills.shop.repositories.OrderRepository;
import by.teachmeskills.shop.utils.ConverterUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private static final String ADD_ORDER_QUERY = "INSERT INTO orders (userId, createdAt, status, price) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM orders";
    private static final String GET_ALL_ORDERS_BY_USER_ID_QUERY = "SELECT * FROM orders WHERE userId = ?";
    private static final String DELETE_ORDER_QUERY = "UPDATE orders SET status = ? WHERE id = ?";

    @Override
    public Order create(Order entity) {
        log.info("Trying to add the new order to the database.");
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(ADD_ORDER_QUERY);

            psInsert.setInt(1, entity.getUserId());
            psInsert.setTimestamp(2, Timestamp.valueOf(entity.getCreatedAt()));
            psInsert.setString(3, entity.getOrderStatus().toString());
            psInsert.setDouble(4, entity.getPrice());
            psInsert.execute();

            pool.closeConnection(connection);
            psInsert.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<Order> read() {
        log.info("Trying to get all orders from the database.");

        List<Order> orders = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_ALL_ORDERS_QUERY);

            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(resultSet.getTimestamp(3).toLocalDateTime())
                        .orderStatus(ConverterUtils.toOrderStatus(resultSet.getString(4)))
                        .price(resultSet.getDouble(5))
                        .build()
                );
            }
            resultSet.close();

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
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
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(DELETE_ORDER_QUERY);

            psDelete.setInt(1, id);
            psDelete.setString(2, String.valueOf(OrderStatus.FINISHED));
            psDelete.execute();

            pool.closeConnection(connection);
            psDelete.close();
        } catch (Exception e) {
            log.error(e.getMessage());
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

    @Override
    public List<Order> findByUserId(int id) {
        log.info("Trying to get all user orders from the database.");

        List<Order> orders = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_ALL_ORDERS_BY_USER_ID_QUERY);
            psGet.setInt(1, id);

            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(resultSet.getTimestamp(3).toLocalDateTime())
                        .orderStatus(ConverterUtils.toOrderStatus(resultSet.getString(4)))
                        .price(resultSet.getDouble(5))
                        .build()
                );
            }
            resultSet.close();

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return orders;
    }
}
