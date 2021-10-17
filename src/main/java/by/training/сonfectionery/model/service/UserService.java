package by.training.сonfectionery.model.service;

import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.ServiceException;


import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> authenticate(String email, String password) throws ServiceException;

    void registrate(Map<String, String> userMap) throws ServiceException;

    boolean validateUserData(Map<String, String> userMap);

    boolean isEmailExist(String email) throws ServiceException;

    boolean verify(String userId) throws ServiceException;

    void updateImage(User user, InputStream inputStream) throws ServiceException;




    void updateUser(User user) throws ServiceException;

    boolean blockUser(int userId) throws ServiceException;
    boolean unblockUser(int userId) throws ServiceException;


}