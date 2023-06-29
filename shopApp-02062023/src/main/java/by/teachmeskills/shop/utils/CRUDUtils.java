package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.OrderStatus;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private final static Logger log = LoggerFactory.getLogger(CRUDUtils.class);
    private static Connection connection;
    private static int lastOrderId;
    private static final String ADD_USER_QUERY = "INSERT INTO users (name, surname, birthday, balance, email, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_ORDER_QUERY = "INSERT INTO orders (user_id, created_at, status, price) VALUES (?, ?, ?, ?)";
    private static final String ADD_PRODUCT_LIST_QUERY = "INSERT INTO product_lists (order_id, product_id) VALUES (?, ?)";
    private static final String GET_LAST_ORDER_ID_QUERY = "SELECT MAX(id) FROM orders";
    private static final String GET_ACTIVE_ORDERS_BY_USER_ID_QUERY = "SELECT * FROM orders WHERE user_id = ?";
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String GET_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String GET_PRODUCTS_BY_ID_QUERY = "SELECT * FROM products WHERE categoryId = ?";
    private static final String GET_PRODUCT_QUERY = "SELECT id, name, description, price, imagePath FROM products WHERE id = ?";
    private static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE users SET password = ? WHERE email = ?";

    private CRUDUtils() {
    }

    public static User getUser(String email) {
        log.info("Trying to get an existing user from the database.");
        User user = null;

        try (PreparedStatement psGet = connection.prepareStatement(GET_USER_QUERY)) {
            psGet.setString(1, email);
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(6))
                        .password(EncryptionUtils.decrypt(resultSet.getString(7)))
                        .build();
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement psGet = connection.prepareStatement(GET_CATEGORIES_QUERY)) {
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    public static List<Product> getCategoryProducts(String categoryId) {
        List<Product> products = new ArrayList<>();

        try (PreparedStatement psGet = connection.prepareStatement(GET_PRODUCTS_BY_ID_QUERY)) {
            psGet.setInt(1, Integer.parseInt(categoryId));
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                products.add(Product.builder()
                        .id((resultSet.getInt(1)))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .imagePath(resultSet.getString(6)).build());
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public static Product getProductById(String productId) {
        Product product = null;

        try (PreparedStatement psGet = connection.prepareStatement(GET_PRODUCT_QUERY)) {
            psGet.setInt(1, Integer.parseInt(productId));
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .imagePath(resultSet.getString(5))
                        .build();
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public static void createUser(User user) {
        log.info("Trying to add a new user to the database.");
        try (PreparedStatement psInsert = connection.prepareStatement(ADD_USER_QUERY)) {
            psInsert.setString(1, user.getName());
            psInsert.setString(2, user.getSurname());
            psInsert.setTimestamp(3, Timestamp.valueOf(user.getBirthday().atStartOfDay()));
            psInsert.setBigDecimal(4, BigDecimal.valueOf(user.getBalance()));
            psInsert.setString(5, user.getEmail());
            psInsert.setString(6, EncryptionUtils.encrypt(user.getPassword()));
            psInsert.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void updateUserPassword(User user) {
        log.info("Trying to change the user password in the database.");
        try (PreparedStatement psUpdate = connection.prepareStatement(UPDATE_USER_PASSWORD_QUERY)) {
            psUpdate.setString(1, EncryptionUtils.encrypt(user.getPassword()));
            psUpdate.setString(2, user.getEmail());
            psUpdate.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void addOrder(Order order) {
        log.info("Trying to add the new order to the database.");
        try (PreparedStatement psInsert = connection.prepareStatement(ADD_ORDER_QUERY)) {
            psInsert.setInt(1, order.getUserId());
            psInsert.setTimestamp(2, Timestamp.valueOf(order.getCreatedAt()));
            psInsert.setString(3, OrderStatus.ACTIVE.toString());
            psInsert.setDouble(4, order.getPrice());

            addProductList(order);

            psInsert.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void addProductList(Order order) {
        getLastOrderId();
        int orderId = ++lastOrderId;

        List<Product> productList = order.getProductList();
        for (Product product : productList) {
            try (PreparedStatement psInsert = connection.prepareStatement(ADD_PRODUCT_LIST_QUERY)) {
                psInsert.setInt(1, orderId);
                psInsert.setInt(2, product.getId());
                psInsert.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void getLastOrderId() {
        try (PreparedStatement psGet = connection.prepareStatement(GET_LAST_ORDER_ID_QUERY)) {
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                lastOrderId = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Order> getActiveOrdersByUserId(User user) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement psGet = connection.prepareStatement(GET_ACTIVE_ORDERS_BY_USER_ID_QUERY)) {
            psGet.setInt(1, user.getId());
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getTimestamp(3).toLocalDateTime(),
                        ConverterUtils.toOrderStatus(resultSet.getString(4)),
                        resultSet.getDouble(5))
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public static void setConnection(ConnectionPool pool) {
        log.info("Trying set the new connection with the database.");
        try {
            connection = pool.getConnection();
            log.info("The connection successfully established.");
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection(ConnectionPool pool) {
        try {
            pool.closeConnection(connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
