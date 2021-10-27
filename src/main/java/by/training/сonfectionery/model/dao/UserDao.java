package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class UserDao extends AbstractDao<Integer, User> {

    public abstract boolean create(User user, String password) throws DaoException;

    public abstract boolean updateUserPassword(User user, String password) throws DaoException;

    public abstract int numberOfRecords() throws DaoException;

    public abstract int numberOfRecords(String[] userStatuses) throws DaoException;

    public abstract Optional<User> findUserByEmail(String email) throws DaoException;

    public abstract List<User> findAll(int offset, int recordsPerPage) throws DaoException;

    public abstract List<User> findUsersByStatusId(int offset, int recordsPerPage, String[] userStatuses) throws DaoException;

    public abstract String findUserPassword(User user) throws DaoException;

}
