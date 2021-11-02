package by.training.сonfectionery.model.service.impl;

import by.training.сonfectionery.model.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.dao.UserDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.UserDaoImpl;
import by.training.сonfectionery.model.service.UserService;
import by.training.сonfectionery.model.validator.impl.UserValidatorImpl;
import by.training.сonfectionery.model.util.Base64Coder;
import by.training.сonfectionery.model.util.IdEncoder;
import by.training.сonfectionery.model.util.PasswordEncoder;
import by.training.сonfectionery.model.util.mail.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.training.сonfectionery.control.command.RequestParameter.*;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();

    private static UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> authenticate(String email, String password) throws ServiceException {
        Optional<User> result = Optional.empty();
        String passwordHash;
        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(userDao);
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            if (optionalUser.isPresent()) {
                passwordHash = userDao.findUserPassword(optionalUser.get());
                if (PasswordEncoder.checkPassword(password, passwordHash)) {
                    result = optionalUser;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in authenticate method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in authenticate method", e);
            }
        }
        return result;
    }


    @Override
    public void register(Map<String, String> userMap) throws ServiceException {

        User user = new User.UserBuilder()
                .setFirstName(userMap.get(FIRST_NAME))
                .setLastName(userMap.get(LAST_NAME))
                .setEmail(userMap.get(EMAIL))
                .setImage(userMap.get(IMAGE))
                .setRole(User.Role.USER)
                .setStatus(User.Status.NON_ACTIVATED)
                .createUser();

        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(userDao);
            userDao.create(user, userMap.get(PASSWORD));
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in register method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in register method", e);
            }
        }
        MailSender mailSender = new MailSender();
        mailSender.send(IdEncoder.encodeId(user.getId()), user.getEmail());
    }

    @Override
    public boolean validateUserData(Map<String, String> userMap) {
        return UserValidatorImpl.getInstance().validateUserData(userMap);
    }

    @Override
    public boolean isEmailExist(String email) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(userDao);
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            if (optionalUser.isPresent()) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in checkEmail method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in checkEmail method", e);
            }
        }
        return false;
    }

    @Override
    public boolean verify(String userId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        User user;
        try {
            transaction.init(userDao);
            Optional<User> optionalUser = userDao.findById(IdEncoder.decodeId(Integer.parseInt(userId)));
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                if (user.getStatus() == User.Status.NON_ACTIVATED) {
                    user.setStatus(User.Status.ACTIVATED);
                    userDao.update(user);
                    return true;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in verify method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in verify method", e);
            }
        }
        return false;
    }

    @Override
    public void updateImage(User user, InputStream inputStream) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        user.setImage(Base64Coder.encode(inputStream));
        try {
            transaction.init(userDao);
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in updateImage method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in updateImage method", e);
            }
        }
    }

    @Override
    public List<User> findUsersByStatusId(int offset, int numberOfRecords, List<Integer> userStatusId) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        List<User> users;
        try {
            transaction.init(userDao);
            users = userDao.findUsersByStatusId(offset, numberOfRecords, userStatusId);
            return users;
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in findUsersByStatusId method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in findUsersByStatusId method", e);
            }
        }
    }

    @Override
    public List<User> findUsers(int offset, int numberOfRecords) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        List<User> users;
        try {
            transaction.init(userDao);
            users = userDao.findAll(offset, numberOfRecords);
            return users;
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in findUsers method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in findUsers method", e);
            }
        }
    }

    @Override
    public Optional<User> findUserById(int id) throws ServiceException {
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        try{
            entityTransaction.init(userDao);
            return userDao.findById(id);
        } catch (DaoException e){
            logger.error("Failed to make transaction in findUserById method", e);
            throw new ServiceException("Failed to make transaction in findUserById method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in findUserById method", e);
            }
        }
    }

    @Override
    public int numberOfRecords() throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(userDao);
            return userDao.numberOfRecords();
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in numberOfRecords method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in numberOfRecords method", e);
            }
        }
    }

    @Override
    public int numberOfRecords(List<Integer> userStatuses) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(userDao);
            return userDao.numberOfRecords(userStatuses);
        } catch (DaoException e) {
            throw new ServiceException("Failed to make transaction in numberOfRecords with product types method", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in numberOfRecords product types method", e);
            }
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        try {
            entityTransaction.init(userDao);
            userDao.update(user);
        } catch (DaoException e) {
            logger.error("Failed to make transaction in updateUser method", e);
            throw new ServiceException("Failed to make transaction in updateUser method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in updateUser method", e);
            }
        }
    }

    @Override
    public boolean checkPassword(User user, String password) throws ServiceException {
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        boolean result;
        try {
            entityTransaction.init(userDao);
            String userPasswordHash = userDao.findUserPassword(user);
            result = PasswordEncoder.checkPassword(password, userPasswordHash);
        } catch (DaoException e) {
            logger.error("Failed to make transaction in checkPassword method", e);
            throw new ServiceException("Failed to make transaction in checkPassword method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in checkPassword method", e);
            }
        }
        return result;
    }

    @Override
    public void updatePassword(User user, String password) throws ServiceException {
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        try {
            entityTransaction.init(userDao);
            userDao.updateUserPassword(user, password);
        } catch (DaoException e) {
            logger.error("Failed to make transaction in updatePassword method", e);
            throw new ServiceException("Failed to make transaction in updatePassword method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in updatePassword method", e);
            }
        }
    }

    @Override
    public void blockUser(int userId) throws ServiceException {
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        try{
            entityTransaction.init(userDao);
            userDao.updateUserStatus(userId, User.Status.BLOCKED);
        } catch (DaoException e){
            logger.error("Failed to make transaction in blockUser method", e);
            throw new ServiceException("Failed to make transaction in blockUser method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in blockUser method", e);
            }
        }
    }

    @Override
    public void unblockUser(int userId) throws ServiceException {
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        try{
            entityTransaction.init(userDao);
            userDao.updateUserStatus(userId, User.Status.ACTIVATED);
        } catch (DaoException e){
            logger.error("Failed to make transaction in unblockUser method", e);
            throw new ServiceException("Failed to make transaction in unblockUser method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in unblockUser method", e);
            }
        }
    }

    @Override
    public void makeUserAdmin(int id) throws ServiceException{
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDao userDao = new UserDaoImpl();
        try{
            entityTransaction.init(userDao);
            userDao.updateUserRole(id, User.Role.ADMIN);
        } catch (DaoException e){
            logger.error("Failed to make transaction in makeUserAdmin method", e);
            throw new ServiceException("Failed to make transaction in makeUserAdmin method", e);
        } finally {
            try {
                entityTransaction.end();
            } catch (DaoException e) {
                logger.error("Can't end transaction in makeUserAdmin method", e);
            }
        }
    }
}
