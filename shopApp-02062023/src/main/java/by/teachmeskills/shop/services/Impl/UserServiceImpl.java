package by.teachmeskills.shop.services.Impl;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.repositories.UserRepository;
import by.teachmeskills.shop.repositories.Impl.UserRepositoryImpl;
import by.teachmeskills.shop.services.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public User create(User entity) {
        return userRepository.create(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.read();
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmailAndPassword(Map<String, String> data) {
        return userRepository.findByEmailAndPassword(data);
    }

    @Override
    public void generateUpdateQuery(Map<String, String> userData, int id) {
        userRepository.generateUpdateQuery(userData, id);
    }
}
