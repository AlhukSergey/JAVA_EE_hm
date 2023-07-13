package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.Product;

import java.util.List;

public interface ProductService extends BaseService<Product>{
    Product getProductById(int id);

    List<Product> getProductsByCategoryId(int categoryId);

    List<Product> getProductsBySearchParameter(String parameter);
}
