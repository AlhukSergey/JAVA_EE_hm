package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.User;

import java.util.Map;

public interface UserService extends BaseService<User> {
    User findById(int id);

    User findByEmailAndPassword(Map<String, String> data);

    void generateUpdateQuery(Map<String, String> userData, int id);
}

