package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private static final String GET_ALL_PRODUCTS_FOR_ORDER = "SELECT * FROM product p " +
            "JOIN order_lists ol ON p.id = ol.productId " +
            "JOIN orders o ON o.id = ol.orderId WHERE o.id = ?";

    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, description, price, categoryId) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM products";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_CATEGORY_ID_QUERY = "SELECT * FROM products WHERE categoryId = ?";
    private static final String GET_PRODUCT_QUANTITY = "SELECT quantity FROM order_lists ol JOIN orders o ON ol.orderId = o.id WHERE orderId = ?";

    @Override
    public Product create(Product entity) {
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(ADD_PRODUCT_QUERY);

            psInsert.setString(1, entity.getName());
            psInsert.setString(2, entity.getDescription());
            psInsert.setDouble(3, entity.getPrice());
            psInsert.setInt(4, entity.getCategoryId());
            psInsert.execute();

            pool.closeConnection(connection);
            psInsert.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<Product> read() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_ALL_PRODUCTS_QUERY);

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

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    @Override
    public Product update(Product entity) {
        //Реализация обновления продукта по типу обновления данных пользователя. Нужно понимать, что обновлять.
        return null;
    }

    @Override
    public void delete(int id) {
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(DELETE_PRODUCT_QUERY);
            psDelete.setInt(1, id);

            psDelete.execute();

            pool.closeConnection(connection);
            psDelete.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY);

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

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();

        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_PRODUCT_BY_CATEGORY_ID_QUERY);

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

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> findBySearchParameter(String parameter) {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(generateSearchQuery(parameter));

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

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    private String generateSearchQuery(String searchParameter) {
        StringBuilder query = new StringBuilder("SELECT * FROM products WHERE (LOWER (name) LIKE '%");

        return query.append(searchParameter)
                .append("%' OR LOWER (description) LIKE '%")
                .append(searchParameter)
                .append("%') ORDER BY name")
                .toString();
    }
}
