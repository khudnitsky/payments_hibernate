/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.constants.ColumnName;
import by.pvt.khudnitsky.payments.constants.SqlRequest;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.ClosingUtil;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.utils.PaymentSystemLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible to get data from a data source which can be
 * database / xml or any other storage mechanism.
 * @author khudnitsky
 * @version 1.0
 *
 */
public class AccountDaoImpl extends AbstractDao<Account> {
    private static AccountDaoImpl instance;
    static String message;

    private AccountDaoImpl(){}

    /**
     * Singleton method
     * @return entity of <b>AccountDaoImpl</b>
     */
    public static synchronized AccountDaoImpl getInstance(){
        if(instance == null){
            instance = new AccountDaoImpl();
        }
        return instance;
    }

    /**
     * Adds new account to the storage
     * @param account - entity of <b>Account</b>
     * @throws DaoException
     */
    @Override
    public void add(Account account) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.ADD_ACCOUNT_WITH_ID);
            statement.setLong(1, account.getId());
            statement.setDouble(2, account.getDeposit());
            statement.setString(3, account.getCurrency());
            statement.setInt(4, account.getAccountStatus());
            statement.executeUpdate();
        }
        catch (SQLException e){
            message = "Unable to add the account ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    /**
     * Gets list of accounts from the storage
     * @return list of accounts
     * @throws DaoException
     */
    @Override
    public List<Account> getAll() throws DaoException {
        List<Account> list = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_ACCOUNTS);
            result = statement.executeQuery();
            while (result.next()) {
                Account account = buildAccount(result);
                list.add(account);
            }
        }
        catch (SQLException e){
            message = "Unable to return list of accounts ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return list;
    }

    /**
     * Gets account by account's id from the storage
     * @param id - account's id
     * @return entity of <b>Account</b>
     * @throws DaoException
     */
    @Override
    public Account getById(Long id) throws DaoException{
        Account account = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ACCOUNT_BY_ID);
            statement.setLong(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                account = buildAccount(result);
            }
        }
        catch(SQLException e){
            message = "Unable to return the account ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return account;
    }

    /**
     * Checks if account is blocked
     * @param id - account's id
     * @return true - if account is blocked, false - otherwise
     * @throws DaoException
     */
    public boolean isAccountStatusBlocked(Long id) throws DaoException{
        boolean isBlocked = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.CHECK_ACCOUNT_STATUS);
            statement.setLong(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                if (result.getInt("status") == 1) {
                    isBlocked = true;
                }
            }
        }
        catch(SQLException e){
            message = "Unable to check account status ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return isBlocked;
    }

    /**
     * Gets list of blocked accounts from the storage
     * @return list of blocked accounts
     * @throws DaoException
     */
    public List<Account> getBlockedAccounts() throws DaoException{
        List<Account> list = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_BLOCKED_ACCOUNTS);
            result = statement.executeQuery();
            while (result.next()) {
                Account account = buildAccount(result);
                list.add(account);
            }
        }
        catch(SQLException e){
            message = "Unable to return list of blocked accounts ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return list;
    }

    /**
     * Gets maximum account's id in the storage
     * @return max account's id
     * @throws DaoException
     */
    @Override
    public Long getMaxId() throws DaoException {
        Long lastId = -1L;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_LAST_ACCOUNT_ID);
            result = statement.executeQuery();
            while (result.next()) {
                lastId = result.getLong(1);
            }
        }
        catch(SQLException e){
            message = "Unable to return max id of accounts ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return lastId;
    }

    /**
     * Updates account
     * @param amount - amount
     * @param id - account's id
     * @throws DaoException
     */
    public void updateAmount(Long id, Double amount) throws DaoException{
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.MAKE_ACCOUNT_OPERATION);
            statement.setDouble(1, amount);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            message = "Unable to update amount ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    /**
     * Updates account
     * @param id - account's id
     * @param status - account's status
     * @throws DaoException
     */

    public void updateAccountStatus(Long id, Integer status) throws DaoException{
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.CHANGE_STATUS);
            statement.setInt(1, status);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            message = "Unable to update account status ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    /**
     * Deletes account from the storage
     * @param id - account's id
     * @throws DaoException
     */
    @Override
    public void delete(Long id)throws DaoException{
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.DELETE_ACCOUNT_BY_ID);
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            message = "Unable to delete the account ";
            PaymentSystemLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    /**
     * Builds account from ResultSet object
     * @param result - ResultSet object
     * @return entity of <b>Account</b>
     * @throws SQLException
     */
    private Account buildAccount(ResultSet result) throws SQLException{
        Long id = result.getLong(ColumnName.ACCOUNT_ID);
        String currency = result.getString(ColumnName.ACCOUNT_CURRENCY);
        Double amount = result.getDouble(ColumnName.ACCOUNT_AMOUNT);
        Integer status = result.getInt(ColumnName.ACCOUNT_STATUS);
        Account account = EntityBuilder.buildAccount(id, currency, amount, status);
        return account;
    }
}