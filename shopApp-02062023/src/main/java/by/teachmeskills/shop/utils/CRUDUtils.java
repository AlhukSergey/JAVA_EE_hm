package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.listener.DBConnectionManager;
import by.teachmeskills.shop.model.Category;
import by.teachmeskills.shop.model.Product;
import by.teachmeskills.shop.model.User;
import jakarta.servlet.ServletContext;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static final String ADD_USER_QUERY = "INSERT INTO users (id, name, surname, balance, email, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String GET_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String GET_PRODUCTS_QUERY = "SELECT * FROM products WHERE categoryId = ?";

    private CRUDUtils() {
    }

    public static User getUser(String email, ServletContext context) {
        User user = null;

        try {
            Connection connection = getConnection(context);
            PreparedStatement psGet = connection.prepareStatement(GET_USER_QUERY);
            psGet.setString(1, email);
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                user = new User(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBigDecimal(4).doubleValue(),
                        resultSet.getString(5),
                        EncryptionUtils.decrypt(resultSet.getString(6)));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static List<Category> getCategories(ServletContext context) {
        List<Category> categories = new ArrayList<>();

        try {
            Connection connection = getConnection(context);
            PreparedStatement psGet = connection.prepareStatement(GET_CATEGORIES_QUERY);
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

    public static List<Product> getCategoryProducts(String categoryId, ServletContext context) {
        List<Product> products = new ArrayList<>();

        try {
            Connection connection = getConnection(context);
            PreparedStatement psGet = connection.prepareStatement(GET_PRODUCTS_QUERY);
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

    public static void createUser(User user, ServletContext context) {
        try {
            Connection connection = getConnection(context);
            PreparedStatement psInsert = connection.prepareStatement(ADD_USER_QUERY);

            psInsert.setString(1, user.getID());
            psInsert.setString(2, user.getName());
            psInsert.setString(3, user.getSurname());
            psInsert.setBigDecimal(4, BigDecimal.valueOf(user.getBalance()));
            psInsert.setString(5, user.getEmail());
            psInsert.setString(6, EncryptionUtils.encrypt(user.getPassword()));
            psInsert.execute();

            psInsert.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection getConnection(ServletContext context) {
        DBConnectionManager dbConnectionManager = (DBConnectionManager) context.getAttribute("DBManager");
        return dbConnectionManager.getConnection();
    }
}
