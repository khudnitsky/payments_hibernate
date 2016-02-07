package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class OperationDaoImplTest {
    private OperationDaoImpl operationDao = OperationDaoImpl.getInstance();
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private AccountDaoImpl accountDao = AccountDaoImpl.getInstance();
    private UserDetailDaoImpl userDetailDao = UserDetailDaoImpl.getInstance();
    private CurrencyDaoImpl currencyDao = CurrencyDaoImpl.getInstance();
    private Operation expectedOperation;
    private Operation actualOperation;
    private User user;
    private UserDetail userDetail;
    private Account account;
    private Currency currency;
    private Serializable operationId;
    private Serializable accountId;
    private Serializable userId;
    private Serializable currencyId;

    @Before
    public void setUp() throws Exception {
        Address address = EntityBuilder.buildAddress("TEST", "TEST", "TEST");
        userDetail = EntityBuilder.buildUserDetail(address);
        user = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", userDetail);
        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        account = EntityBuilder.buildAccount(200D, AccountStatusType.UNBLOCKED, currency, user);
        expectedOperation = EntityBuilder.buildOperation(200D, "TEST", Calendar.getInstance(), user, account);
    }

    @Test
    public void testSave() throws Exception {
        persistEntities();
        expectedOperation.setId((Long) operationId);
        actualOperation = operationDao.getById((Long) operationId);
        Assert.assertEquals("save() method failed: ", expectedOperation, actualOperation);
        delete();
    }

    @Test
    public void testGetAll() throws Exception {
        Long expectedSize = (long) operationDao.getAll().size();
        Long actualSize = operationDao.getAmount();
        Assert.assertEquals("getAll() method failed: ", expectedSize, actualSize);
    }

    @Test
    public void testGetById() throws Exception {
        persistEntities();
        expectedOperation.setId((Long) operationId);
        actualOperation = operationDao.getById((Long) operationId);
        Assert.assertEquals("getById() method failed: ", expectedOperation, actualOperation);
        delete();
    }


    @Test
    public void testUpdate() throws Exception {
        persistEntities();
        expectedOperation.setId((Long) operationId);
        expectedOperation.setAmount(2000D);
        operationDao.update(expectedOperation);
        actualOperation = operationDao.getById((Long) operationId);
        Assert.assertEquals("update() method failed: ", expectedOperation, actualOperation);
        delete();
    }

    @Test
    public void testDelete() throws Exception {
        persistEntities();
        delete();
        actualOperation = operationDao.getById((Long) operationId);
        Assert.assertNull("delete() method failed: ", actualOperation);
    }

    @After
    public void tearDown() throws Exception{
        expectedOperation = null;
        actualOperation = null;
        account = null;
        user = null;
        userDetail = null;
        currency = null;
        operationId = null;
        accountId = null;
        userId = null;
        currencyId = null;
    }

    private void persistEntities() throws Exception {
        userDetailDao.save(userDetail);
        userId = userDao.save(user);
        currencyId = currencyDao.save(currency);
        accountId = accountDao.save(account);
        operationId = operationDao.save(expectedOperation);
    }

    private void delete() throws by.pvt.khudnitsky.payments.exceptions.DaoException {
        operationDao.delete((Long) operationId);
        accountDao.delete((Long) accountId);
        userDao.delete((Long) userId);
        currencyDao.delete((Long) currencyId);
    }
}