package by.teachmeskills.shop.services.Impl;

import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.repositories.Impl.ProductRepositoryImpl;
import by.teachmeskills.shop.repositories.ProductRepository;
import by.teachmeskills.shop.services.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    @Override
    public Product create(Product entity) {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsBySearchParameter(String parameter) {
        return productRepository.findBySearchParameter(parameter);
    }
}
