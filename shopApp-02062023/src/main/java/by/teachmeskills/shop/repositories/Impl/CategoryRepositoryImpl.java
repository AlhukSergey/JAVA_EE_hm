package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private static final String ADD_CATEGORY_QUERY = "INSERT INTO categories (name) VALUES (?)";
    private static final String GET_ALL_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String UPDATE_CATEGORY_QUERY = "UPDATE categories SET name = ? WHERE id = ?";
    private static final String DELETE_CATEGORY_QUERY = "DELETE FROM categories WHERE id = ?";
    private static final String GET_CATEGORY_BY_ID_QUERY = "SELECT * FROM categories WHERE id = ?";

    @Override
    public Category create(Category entity) {
        log.info("Trying to add a new category to the database.");

        try {
            Connection connection = pool.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(ADD_CATEGORY_QUERY);

            psInsert.setString(1, entity.getName());
            psInsert.execute();

            pool.closeConnection(connection);
            psInsert.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<Category> read() {
        log.info("Trying to get all categories from the database.");

        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_ALL_CATEGORIES_QUERY);

            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                categories.add(Category.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .build()
                );
            }
            resultSet.close();

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return categories;
    }

    @Override
    public Category update(Category entity) {
        log.info("Trying to change the category data in the database.");
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(UPDATE_CATEGORY_QUERY);

            psUpdate.setInt(1, entity.getId());
            psUpdate.setString(2, entity.getName());
            psUpdate.execute();

            pool.closeConnection(connection);
            psUpdate.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        log.info("Trying to delete the category from the database.");
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(DELETE_CATEGORY_QUERY);

            psDelete.setInt(1, id);
            psDelete.execute();

            pool.closeConnection(connection);
            psDelete.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Category findById(int id) {
        log.info("Trying to get the category from the database.");

        Category category = null;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_CATEGORY_BY_ID_QUERY);

            psGet.setInt(1, id);
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                category = Category.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .build();
            }
            resultSet.close();

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return category;
    }
}
