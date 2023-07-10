package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.Image;

import java.util.List;

public interface ImageService extends BaseService<Image> {
    Image findById(int id);
    Image findByCategoryId(int categoryId);
    List<Image> findByProductId(int productId);
}
