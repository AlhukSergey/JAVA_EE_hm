package by.teachmeskills.shop.repositories;

import by.teachmeskills.shop.domain.User;

import java.util.Map;

public interface UserRepository extends BaseRepository<User> {
    User findById(int id);
    User findByEmailAndPassword(Map<String, String> data);
    void generateUpdateQuery(Map<String, String> data, int id);
}
