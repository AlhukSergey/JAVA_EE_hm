package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.User;

import java.util.Map;

public interface UserService extends BaseService<User> {
    User getUserById(int id);

    User getUserByEmailAndPassword(Map<String, String> data);

    void generateForUpdate(Map<String, String> userData, int id);
}

