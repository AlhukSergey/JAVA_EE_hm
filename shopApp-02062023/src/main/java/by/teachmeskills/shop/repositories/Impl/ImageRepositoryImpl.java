package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.repositories.ImageRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ImageRepositoryImpl implements ImageRepository {
    private static final String GET_IMAGE_BY_CATEGORY_ID_QUERY = "SELECT * FROM images WHERE categoryId = ?";
    private static final String GET_IMAGES_BY_PRODUCT_ID_QUERY = "SELECT * FROM images WHERE productId = ?";

    @Override
    public Image create(Image entity) {
        return null;
    }

    @Override
    public List<Image> read() {
        return null;
    }

    @Override
    public Image update(Image entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Image findById(int id) {
        return null;
    }

    @Override
    public Image findByCategoryId(int categoryId) {
        log.info("Trying to get the image from the database.");

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
            log.error(e.getMessage());
        }
        return image;
    }

    @Override
    public List<Image> findByProductId(int productId) {
        log.info("Trying to get the image from the database.");

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
            log.error(e.getMessage());
        }
        return images;
    }
}
