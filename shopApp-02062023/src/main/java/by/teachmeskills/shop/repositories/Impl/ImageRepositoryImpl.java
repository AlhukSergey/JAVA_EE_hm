package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.repositories.ImageRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ImageRepositoryImpl implements ImageRepository {
    private static final String ADD_IMAGE_QUERY = "INSERT INTO images (imagePath, categoryId, productId, primaryImage) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_IMAGES_QUERY = "SELECT * FROM images";
    private static final String UPDATE_IMAGE_QUERY = "UPDATE images SET imagePath = ? WHERE id = ?";
    private static final String DELETE_IMAGE_QUERY = "DELETE FROM images WHERE id = ?";
    private static final String GET_IMAGE_BY_ID_QUERY = "SELECT * FROM images WHERE id = ?";
    private static final String GET_IMAGE_BY_CATEGORY_ID_QUERY = "SELECT * FROM images WHERE categoryId = ?";
    private static final String GET_IMAGES_BY_PRODUCT_ID_QUERY = "SELECT * FROM images WHERE productId = ?";

    @Override
    public Image create(Image entity) {
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(ADD_IMAGE_QUERY);

            psInsert.setString(1, entity.getImagePath());
            psInsert.setInt(2, entity.getCategoryId());
            psInsert.setInt(3, entity.getProductId());
            psInsert.setInt(4, entity.getPrimary());
            psInsert.execute();

            pool.closeConnection(connection);
            psInsert.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<Image> read() {
        List<Image> images = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_ALL_IMAGES_QUERY);

            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                images.add(Image.builder()
                        .id(resultSet.getInt(1))
                        .imagePath(resultSet.getString(2))
                        .categoryId(resultSet.getInt(3))
                        .productId(resultSet.getInt(4))
                        .primary(resultSet.getInt(5))
                        .build()
                );
            }

            resultSet.close();
            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return images;
    }

    @Override
    public Image update(Image entity) {
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(UPDATE_IMAGE_QUERY);

            psUpdate.setString(1, entity.getImagePath());
            psUpdate.setInt(2, entity.getId());

            psUpdate.execute();

            pool.closeConnection(connection);
            psUpdate.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(DELETE_IMAGE_QUERY);
            psDelete.setInt(1, id);

            psDelete.execute();

            pool.closeConnection(connection);
            psDelete.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Image findById(int id) {
        Image image = null;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_IMAGE_BY_ID_QUERY);

            psGet.setInt(1, id);
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                image = Image.builder()
                        .id(resultSet.getInt("id"))
                        .imagePath(resultSet.getString("imagePath"))
                        .categoryId(resultSet.getInt("categoryId"))
                        .productId(resultSet.getInt("productId"))
                        .primary(resultSet.getInt("primaryImage"))
                        .build();
            }
            resultSet.close();

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

    @Override
    public Image findByCategoryId(int categoryId) {
        Image image = null;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_IMAGE_BY_CATEGORY_ID_QUERY);

            psGet.setInt(1, categoryId);
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                image = Image.builder()
                        .id(resultSet.getInt("id"))
                        .imagePath(resultSet.getString("imagePath"))
                        .categoryId(resultSet.getInt("categoryId"))
                        .build();
            }
            resultSet.close();

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

    @Override
    public List<Image> findByProductId(int productId) {
        List<Image> images = new ArrayList<>();
        try {
            Connection connection = pool.getConnection();
            PreparedStatement psGet = connection.prepareStatement(GET_IMAGES_BY_PRODUCT_ID_QUERY);

            psGet.setInt(1, productId);
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                images.add(Image.builder()
                        .id(resultSet.getInt("id"))
                        .imagePath(resultSet.getString("imagePath"))
                        .categoryId(resultSet.getInt("categoryId"))
                        .productId(resultSet.getInt("productId"))
                        .primary(resultSet.getInt("primaryImage"))
                        .build()
                );
            }
            resultSet.close();

            pool.closeConnection(connection);
            psGet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return images;
    }
}
