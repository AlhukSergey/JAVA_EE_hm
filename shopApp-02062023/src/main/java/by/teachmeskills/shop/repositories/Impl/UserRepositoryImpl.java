package by.teachmeskills.shop.repositories.Impl;

import by.teachmeskills.shop.commands.enums.MapKeysEnum;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.repositories.UserRepository;
import by.teachmeskills.shop.utils.EncryptionUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private static String updateQuery;
    private static final String ADD_USER_QUERY = "INSERT INTO users (name, surname, birthday, balance, email, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String GET_USER_BY_EMAIL_AND_PASS_QUERY = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final Map<String, String> usersTableColumnNames = Map.of(
            MapKeysEnum.ID.getKey(), "id",
            MapKeysEnum.NAME.getKey(), "name",
            MapKeysEnum.SURNAME.getKey(), "surname",
            MapKeysEnum.BIRTHDAY.getKey(), "birthday",
            MapKeysEnum.BALANCE.getKey(), "balance",
            MapKeysEnum.EMAIL.getKey(), "email",
            MapKeysEnum.PASSWORD.getKey(), "password");

    @Override
    public User create(User entity) {
        log.info("Trying to add a new user to the database.");

        try (Connection connection = pool.getConnection();
             PreparedStatement psInsert = connection.prepareStatement(ADD_USER_QUERY)) {
            psInsert.setString(1, entity.getName());
            psInsert.setString(2, entity.getSurname());
            psInsert.setTimestamp(3, Timestamp.valueOf(entity.getBirthday().atStartOfDay()));
            psInsert.setBigDecimal(4, BigDecimal.valueOf(entity.getBalance()));
            psInsert.setString(5, entity.getEmail());
            psInsert.setString(6, EncryptionUtils.encrypt(entity.getPassword()));
            psInsert.execute();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<User> read() {
        log.info("Trying to get all users from the database.");

        List<User> users = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_ALL_USERS_QUERY)) {
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(5))
                        .password(EncryptionUtils.decrypt(resultSet.getString(6)))
                        .build()
                );
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }

    @Override
    public User update(User entity) {
        log.info("Trying to change the user data in the database.");
        try (Connection connection = pool.getConnection();
             PreparedStatement psUpdate = connection.prepareStatement(updateQuery)) {
            psUpdate.execute();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        log.info("Trying to delete the user from the database.");
        try (Connection connection = pool.getConnection();
             PreparedStatement psDelete = connection.prepareStatement(DELETE_USER_QUERY)) {
            psDelete.setInt(1, id);
            psDelete.execute();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public User findById(int id) {
        log.info("Trying to get the user from the database.");

        User user = null;
        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_USER_BY_ID_QUERY)) {
            psGet.setInt(1, id);
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(5))
                        .password(EncryptionUtils.decrypt(resultSet.getString(6)))
                        .build();
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }

    @Override
    public User findByEmailAndPassword(Map<String, String> data) {
        log.info("Trying to get an existing user from the database.");
        User user = null;

        try (Connection connection = pool.getConnection();
             PreparedStatement psGet = connection.prepareStatement(GET_USER_BY_EMAIL_AND_PASS_QUERY)) {
            psGet.setString(1, data.get("email"));
            psGet.setString(2, EncryptionUtils.encrypt(data.get("password")));
            ResultSet resultSet = psGet.executeQuery();

            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(6))
                        .password(EncryptionUtils.decrypt(resultSet.getString(7)))
                        .build();
            }
            resultSet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }

    @Override
    public void generateUpdateQuery(Map<String, String> userData, int userId) {
        StringBuilder query = new StringBuilder("UPDATE users SET ");

        if (userData.containsKey(MapKeysEnum.NEW_PASSWORD.getKey())) {
            updateQuery = query
                    .append(usersTableColumnNames.get(MapKeysEnum.PASSWORD.getKey()))
                    .append(" = '")
                    .append(EncryptionUtils.encrypt(userData.get(MapKeysEnum.NEW_PASSWORD.getKey())))
                    .append(" WHERE id = '")
                    .append(userId)
                    .append("'").toString();
        }

        for (Map.Entry<String, String> name : usersTableColumnNames.entrySet()) {
            if (userData.containsKey(name.getKey())) {
                query
                        .append(name.getKey())
                        .append(" = '")
                        .append(userData.get(name.getKey()))
                        .append("', ");
            }
        }

        query.deleteCharAt(query.lastIndexOf(","));

        updateQuery = query.append(" WHERE id = '").append(userId).append("'").toString();
    }
}
