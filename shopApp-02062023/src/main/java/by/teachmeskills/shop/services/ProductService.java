package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.Product;

import java.util.List;

public interface ProductService extends BaseService<Product>{
    Product findById(int id);

    List<Product> findByCategoryId(int categoryId);
}
