package by.training.сonfectionery.model.dao;

import by.training.сonfectionery.model.domain.User;
import by.training.сonfectionery.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class UserDao extends AbstractDao<Integer, User> {

    public abstract boolean create(User user, String password) throws DaoException;

    public abstract void updateUserPassword(User user, String password) throws DaoException;

    public abstract void updateUserStatus(int id, User.Status status) throws DaoException;

    public abstract void updateUserRole(int id, User.Role role) throws DaoException;

    public abstract int numberOfRecords() throws DaoException;

    public abstract int numberOfRecords(List<Integer> userStatuses) throws DaoException;

    public abstract Optional<User> findUserByEmail(String email) throws DaoException;

    public abstract List<User> findAll(int offset, int recordsPerPage) throws DaoException;

    public abstract List<User> findUsersByStatusId(int offset, int recordsPerPage, List<Integer> userStatuses) throws DaoException;

    public abstract String findUserPassword(User user) throws DaoException;

}
