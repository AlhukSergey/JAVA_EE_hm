package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.repositories.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private static String GET_ALL_PRODUCTS_FOR_ORDER = "SELECT * FROM product p " +
            "JOIN order_lists ol ON p.id = ol.productId " +
            "JOIN orders o ON o.id = ol.orderId WHERE o.id = ?";

    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, description, price, categoryId) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM products";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_CATEGORY_ID_QUERY = "SELECT * FROM products WHERE categoryId = ?";

    private static String GET_PRODUCT_QUANTITY = "SELECT quantity FROM order_lists ol JOIN orders o ON ol.orderId = o.id WHERE orderId = ?";

    @Override
    public Product create(Product entity) {
        log.info("Trying to add a new product to the database.");

        try (Connection connection = pool.getConnection();
             PreparedStatement psInsert = connection.prepareStatement(ADD_PRODUCT_QUERY)) {
            psInsert.setString(1, entity.getName());
            psInsert.setString(2, entity.getDescription());
            psInsert.setDouble(3, entity.getPrice());
            psInsert.setInt(4, entity.getCategoryId());
            psInsert.execute();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<Product> read() {
        log.info("Trying to get all products from the database.");

        List<Product> products = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_ALL_PRODUCTS_QUERY)) {
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                products.add(Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .build()
                );
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return products;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public Product findById(int id) {
        log.info("Trying to get the product from the database.");

        Product product = null;
        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY)) {
            psGet.setInt(1, id);
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .build();
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return product;
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        log.info("Trying to get all the category products from the database.");
        List<Product> products = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_PRODUCT_BY_CATEGORY_ID_QUERY)) {
            psGet.setInt(1, categoryId);
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                products.add(Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .categoryId(resultSet.getInt("categoryId"))
                        .build());
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return products;
    }
}
