package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.model.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.UserDao;
import by.training.сonfectionery.model.util.Base64Coder;
import by.training.сonfectionery.model.util.PasswordEncoder;

import static by.training.сonfectionery.model.dao.ColumnName.*;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDaoImpl extends UserDao {

    private static final String SQL_FIND_ALL_USERS = """
            SELECT users.id, first_name, last_name, email, image, role, status
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
    private static final String SQL_FIND_USER = """
            SELECT users.id, first_name, last_name, email, image, role, status
            FROM users
            JOIN role ON users.role_id = role.id
            JOIN status ON users.status_id = status.id
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

    private static final String SQL_UPDATE_USER_STATUS = """
            UPDATE users
            SET status_id = ?
            WHERE id = ?;
            """;

    private static final String SQL_UPDATE_USER_ROLE = """
            UPDATE users
            SET role_id = ?
            WHERE id = ?;
            """;

    private static final String SQL_FIND_USER_PASSWORD = """
            SELECT password
            FROM users
            WHERE id = ?;
            """;

    private static final String SQL_GET_NUMBER_OF_RECORDS = """
            SELECT COUNT(*) as count
            FROM users
            """;


    @Override
    public List<User> findAll(int offset, int recordsPerPage) throws DaoException {
        String query = SQL_FIND_USER + "\nLIMIT ?, ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, recordsPerPage);
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
    public List<User> findUsersByStatusId(int offset, int recordsPerPage, List<Integer> userStatusId) throws DaoException {
        StringBuilder query = new StringBuilder(SQL_FIND_USER + "WHERE status_id IN (");
        query.append("?,".repeat(userStatusId.size()));
        query = new StringBuilder(query.substring(0, query.length() - 1) + ")\nLIMIT ?, ?;");
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            List<User> users = new ArrayList<>();
            int i;
            for (i = 0; i < userStatusId.size(); i++) {
                statement.setInt(i + 1, userStatusId.get(i));
            }
            statement.setInt(i + 1, offset);
            statement.setInt(i + 2, recordsPerPage);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = buildUser(resultSet);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find users by status_id", e);
        }
    }


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
    public Optional<User> findById(Integer id) throws DaoException {
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
    public void deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
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
            if (user.getImage() != null) {
                statement.setBlob(5, Base64Coder.decode(user.getImage()));
            } else {
                statement.setBlob(5, (InputStream) null);
            }
            statement.setInt(6, user.getRole().getId());
            statement.setInt(7, user.getStatus().getId());
            boolean result = statement.executeUpdate() == 1;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
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
    public void updateUserPassword(User user, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            statement.setString(1, PasswordEncoder.encodePassword(password));
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update user password", e);
        }
    }

    @Override
    public void updateUserStatus(int id, User.Status status) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_STATUS)) {
            statement.setInt(1, status.getId());
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update user status", e);
        }
    }

    @Override
    public void updateUserRole(int id, User.Role role) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_ROLE)) {
            statement.setInt(1, role.getId());
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update user role", e);
        }
    }

    @Override
    public int numberOfRecords() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_NUMBER_OF_RECORDS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find number of users", e);
        }
    }

    @Override
    public int numberOfRecords(List<Integer> userStatuses) throws DaoException {
        StringBuilder query = new StringBuilder(SQL_GET_NUMBER_OF_RECORDS + "WHERE status_id IN (");
        query.append("?,".repeat(userStatuses.size()));
        query = new StringBuilder(query.substring(0, query.length() - 1) + ")");
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < userStatuses.size(); i++) {
                statement.setInt(i + 1, userStatuses.get(i));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find number of products with product_type_id", e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
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
            try (ResultSet resultSet = statement.executeQuery()) {
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
