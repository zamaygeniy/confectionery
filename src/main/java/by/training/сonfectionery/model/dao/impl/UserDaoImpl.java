package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.UserDao;
import by.training.сonfectionery.util.Base64Coder;
import by.training.сonfectionery.util.PasswordEncoder;

import static by.training.сonfectionery.model.dao.ColumnName.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDaoImpl extends UserDao {

    private static final String SQL_FIND_ALL_USERS = """
            SELECT id, first_name, last_name, email, image, role, status
            FROM users
            JOIN role ON users.role_id = role.id
            JOIN status ON users.status_id = status.id;
            """;
    private static final String SQL_FIND_USER_BY_ID = """
            SELECT users.id, first_name, last_name, email, image, role, status
            FROM users
            JOIN role ON users.role_id = role.id
            JOIN status ON users.status_id = status.id
            WHERE users.id = ?;
            """;
    private static final String SQL_FIND_USER_BY_EMAIL = """
            SELECT users.id, first_name, last_name, email, image, role, status
            FROM users
            JOIN role ON users.role_id = role.id
            JOIN status ON users.status_id = status.id
            WHERE email = ?;
            """;
    private static final String SQL_DELETE_USER_BY_ID = """
            DELETE FROM users WHERE id = ?;
            """;
    private static final String SQL_CREATE_USER = """
            INSERT INTO users (first_name, last_name, email, password, image, role_id, status_id)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String SQL_UPDATE_USER = """
            UPDATE users
            SET first_name = ?, last_name = ?, image = ?, role_id = ?, status_id = ?
            WHERE users.id = ?;
            """;
    private static final String SQL_UPDATE_USER_PASSWORD = """
            UPDATE users
            SET password = ?
            WHERE id = ?;
            """;
    private static final String SQL_FIND_USER_PASSWORD = """
            SELECT password
            FROM users
            WHERE id = ?;
            """;

    @Override
    public List<User> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            List<User> users = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = buildUser(resultSet);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all users", e);
        }
    }

    @Override
    public Optional<User>  findById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = buildUser(resultSet);
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to find user by id", e);
        }
    }


    @Override
    public boolean deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete user by id", e);
        }
    }

    @Override
    public boolean create(User user) throws DaoException {
        throw new UnsupportedOperationException("This method unavailable in UserDao," +
                " use method create(User user, String password)");
    }

    @Override
    public boolean create(User user, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, PasswordEncoder.encodePassword(password));
            statement.setBlob(5, Base64Coder.decode(user.getImage()));
            statement.setInt(6, user.getRole().getId());
            statement.setInt(7, user.getStatus().getId());
            boolean result = statement.executeUpdate() == 1;
            try(ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
                return result;
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to create user", e);
        }
    }

    @Override
    public User update(User user) throws DaoException {
        User oldUser = findById(user.getId()).
                orElseThrow(() -> new DaoException("Failed to update user, user's id was not found"));
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setBlob(3, Base64Coder.decode(user.getImage()));
            statement.setInt(4, user.getRole().getId());
            statement.setInt(5, user.getStatus().getId());
            statement.setInt(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update user", e);
        }
        return oldUser;
    }

    @Override
    public boolean updateUserPassword(User user, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            statement.setString(1, password);
            statement.setInt(2, user.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to update user password", e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    User user = buildUser(resultSet);
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to find user by email", e);
        }
    }

    @Override
    public String findUserPassword(User user) throws DaoException {
        String passwordHash;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_PASSWORD)) {
            statement.setInt(1, user.getId());
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    passwordHash = resultSet.getString(PASSWORD);
                } else throw new DaoException("Can't find user with this id");
                return passwordHash;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user password", e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {

        return new User.UserBuilder()
                .setId(resultSet.getInt(ID))
                .setFirstName(resultSet.getString(FIRST_NAME))
                .setLastName(resultSet.getString(LAST_NAME))
                .setEmail(resultSet.getString(EMAIL))
                .setImage(resultSet.getBlob(IMAGE) == null ? "" : Base64Coder.encode(resultSet.getBlob(IMAGE).getBinaryStream()))
                .setRole(User.Role.valueOf(resultSet.getString(ROLE).toUpperCase(Locale.ROOT)))
                .setStatus(User.Status.valueOf(resultSet.getString(STATUS).toUpperCase(Locale.ROOT)))
                .createUser();
    }
}
