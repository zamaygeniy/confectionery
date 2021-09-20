package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;

import java.util.Optional;

public abstract class UserDao extends AbstractDao<Integer, User> {

    public abstract boolean create(User user, String password) throws DaoException;

    public abstract boolean updateUserPassword(User user, String password) throws DaoException;

    public abstract Optional<User> findUserByEmail(String email) throws DaoException;

    public abstract String findUserPassword(User user) throws DaoException;

}
