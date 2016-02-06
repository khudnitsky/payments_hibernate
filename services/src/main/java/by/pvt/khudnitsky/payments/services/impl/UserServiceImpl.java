package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.enums.AccessLevelEnum;
import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.PaymentSystemLogger;

import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserServiceImpl extends AbstractService<User> {
    private static UserServiceImpl instance;

    private UserServiceImpl(){}

    public static synchronized UserServiceImpl getInstance(){
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

    /**
     * Calls Dao add() method
     *
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public void add(User entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public List<User> getAll() throws SQLException, ServiceException {
        List<User> users = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            users = UserDaoImpl.getInstance().getAll();
            connection.commit();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction succeeded");
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        }
        return users;
    }

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public User getById(Long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public void update(User entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao delete() method
     *
     * @param id - id of entity
     * @throws SQLException
     */
    @Override
    public void delete(Long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public boolean checkUserAuthorization(String login, String password) throws SQLException, ServiceException {
        boolean isAuthorized = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            isAuthorized = UserDaoImpl.getInstance().isAuthorized(login, password);
            connection.commit();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction succeeded");
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        }
        return isAuthorized;
    }

    public User getUserByLogin(String login) throws SQLException, ServiceException {
        User user = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            user = UserDaoImpl.getInstance().getByLogin(login);
            connection.commit();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction succeeded");
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    public AccessLevelEnum checkAccessLevel(User user) throws SQLException{
        AccessLevelEnum accessLevelEnum;
        if(AccessLevelEnum.CLIENT.ordinal() == user.getAccessLevelEnum()){
            accessLevelEnum = AccessLevelEnum.CLIENT;
        }
        else{
            accessLevelEnum = AccessLevelEnum.ADMINISTRATOR;
        }
        return accessLevelEnum;
    }

    public boolean checkIsNewUser(User user) throws SQLException, ServiceException {
        boolean isNew = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            if ((AccountDaoImpl.getInstance().getById(user.getAccountId()) == null) & (UserDaoImpl.getInstance().isNewUser(user.getLogin()))) {
                isNew = true;
            }
            connection.commit();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction succeeded");
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        }
        //PoolManager.getInstance().releaseConnection(connection);
        return isNew;
    }

    public void registrateUser(User user, Account account) throws SQLException, ServiceException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            AccountDaoImpl.getInstance().add(account);
            UserDaoImpl.getInstance().add(user);
            connection.commit();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction succeeded");
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            PaymentSystemLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        }
    }
}
