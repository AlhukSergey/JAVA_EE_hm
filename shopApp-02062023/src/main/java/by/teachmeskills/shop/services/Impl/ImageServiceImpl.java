package by.teachmeskills.shop.services.Impl;

import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.repositories.ImageRepository;
import by.teachmeskills.shop.repositories.Impl.ImageRepositoryImpl;
import by.teachmeskills.shop.services.ImageService;

import java.util.List;

public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository = new ImageRepositoryImpl();
    @Override
    public Image create(Image entity) {
        return imageRepository.create(entity);
    }

    @Override
    public List<Image> read() {
        return imageRepository.read();
    }

    @Override
    public Image update(Image entity) {
        return imageRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        imageRepository.delete(id);
    }

    @Override
    public Image findById(int id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image findByCategoryId(int categoryId) {
        return imageRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Image> findByProductId(int productId) {
        return imageRepository.findByProductId(productId);
    }
}
