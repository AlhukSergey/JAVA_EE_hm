package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.User;

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
    private static final String ADD_USER_QUERY = "INSERT INTO users (name, surname, birthday, balance, email, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_QUERY = "SELECT name, surname, birthday, balance, email, password FROM users WHERE email = ?";
    private static final String GET_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String GET_PRODUCTS_BY_ID_QUERY = "SELECT * FROM products WHERE categoryId = ?";
    private static final String GET_PRODUCT_QUERY = "SELECT id, name, description, price, imagePath FROM products WHERE id = ?";

    private CRUDUtils() {
    }

    public static User getUser(String email) {
        User user = null;

        try (PreparedStatement psGet = connection.prepareStatement(GET_USER_QUERY)) {
            psGet.setString(1, email);
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                user = User.builder()
                        .name(resultSet.getString(1))
                        .surname(resultSet.getString(2))
                        .birthday(resultSet.getTimestamp(3).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(4).doubleValue())
                        .email(resultSet.getString(5))
                        .password(EncryptionUtils.decrypt(resultSet.getString(6)))
                        .build();
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

        try (PreparedStatement psGet = connection.prepareStatement(GET_PRODUCTS_BY_ID_QUERY)) {
            psGet.setInt(1, Integer.parseInt(categoryId));
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                /*products.add(Product.builder()
                        .id((resultSet.getInt(1)))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .imagePath(resultSet.getString(6)).build());*/
                products.add(Product.newBuilder()
                        .withId((resultSet.getInt(1)))
                        .withName(resultSet.getString(2))
                        .withDescription(resultSet.getString(3))
                        .withPrice(resultSet.getDouble(4))
                        .withCategoryId(resultSet.getInt(5))
                        .withImagePath(resultSet.getString(6)).build());
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



            // Использование lombok builder приводит к ошибке компиляции. Программа работает, все запускается, но
            // idea подсвечивает ошибку "Lombok builder is missing non nullable fields" и в качестве решения
            // предлагает add all mandatory fields, но полей, объявленных как обязательные, у меня нет.
            // Не нашел ни в документации @builder ни в сети.
            // При реализации паттерна Builder руками подобной ошибки не возникает.
            // Предположу, что это связано с работой lombok builder и sql.

            while (resultSet.next()) {
                /*product = Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .imagePath(resultSet.getString(5))
                        .build();*/
                product = Product.newBuilder()
                        .withId(resultSet.getInt(1))
                        .withName(resultSet.getString(2))
                        .withDescription(resultSet.getString(3))
                        .withPrice(resultSet.getDouble(4))
                        .withImagePath(resultSet.getString(5))
                        .build();
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public static void createUser(User user) {
        try (PreparedStatement psInsert = connection.prepareStatement(ADD_USER_QUERY)) {
            psInsert.setString(1, user.getName());
            psInsert.setString(2, user.getSurname());
            psInsert.setTimestamp(3, Timestamp.valueOf(user.getBirthday().atStartOfDay()));
            psInsert.setBigDecimal(4, BigDecimal.valueOf(user.getBalance()));
            psInsert.setString(5, user.getEmail());
            psInsert.setString(6, EncryptionUtils.encrypt(user.getPassword()));
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

    public static void closeConnection(ConnectionPool pool) {
        try {
            pool.closeConnection(connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
