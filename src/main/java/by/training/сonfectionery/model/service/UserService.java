package by.training.сonfectionery.model.service;

import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.ServiceException;


import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> authenticate(String email, String password) throws ServiceException;

    void registrate(Map<String, String> userMap) throws ServiceException;

    boolean validateUserData(Map<String, String> userMap);

    boolean isEmailExist(String email) throws ServiceException;

    boolean verify(String userId) throws ServiceException;

    void updateImage(User user, InputStream inputStream) throws ServiceException;

    List<User> findUsersByStatusId(int offset, int numberOfRecords, String[] userStatusId) throws ServiceException;

    List<User> findUsers(int offset, int recordsPerPage) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    int numberOfRecords() throws ServiceException;

    int numberOfRecords(String[] userStatuses) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    boolean checkPassword(User user, String password) throws ServiceException;

    void updatePassword(User user, String password) throws ServiceException;

    boolean blockUser(int userId) throws ServiceException;

    boolean unblockUser(int userId) throws ServiceException;


}