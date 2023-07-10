package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.Category;

public interface CategoryService extends BaseService<Category> {
    Category findById(int id);
}
