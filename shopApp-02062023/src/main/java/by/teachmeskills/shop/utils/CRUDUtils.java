package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.model.Category;
import by.teachmeskills.shop.model.Product;
import by.teachmeskills.shop.model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static Connection connection;
    private static final String ADD_USER_QUERY = "INSERT INTO users (id, name, surname, birthday, balance, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String GET_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String GET_PRODUCTS_QUERY = "SELECT * FROM products WHERE categoryId = ?";
    private static final String GET_PRODUCT_QUERY = "SELECT name, description, price, imagePath FROM products WHERE id = ?";

    private CRUDUtils() {
    }

    public static User getUser(String email) {
        User user = null;

        try (PreparedStatement psGet = connection.prepareStatement(GET_USER_QUERY)) {
            psGet.setString(1, email);
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                user = new User(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getTimestamp(4).toLocalDateTime().toLocalDate(),
                        resultSet.getBigDecimal(5).doubleValue(),
                        resultSet.getString(6),
                        EncryptionUtils.decrypt(resultSet.getString(7)));
            }
            resultSet.close();
        } catch (SQLException e) {
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

        try (PreparedStatement psGet = connection.prepareStatement(GET_PRODUCTS_QUERY)) {
            psGet.setInt(1, Integer.parseInt(categoryId));
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)));
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
                product = new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4)
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public static void createUser(User user) {
        try (PreparedStatement psInsert = connection.prepareStatement(ADD_USER_QUERY)) {
            psInsert.setString(1, user.getID());
            psInsert.setString(2, user.getName());
            psInsert.setString(3, user.getSurname());
            psInsert.setTimestamp(4, Timestamp.valueOf(user.getBirthday().atStartOfDay()));
            psInsert.setBigDecimal(5, BigDecimal.valueOf(user.getBalance()));
            psInsert.setString(6, user.getEmail());
            psInsert.setString(7, EncryptionUtils.encrypt(user.getPassword()));
            psInsert.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setConnection(ConnectionPool pool) {
        try {
            connection = pool.getConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
