package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.enums.AccountStatusEnum;
import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class AccountServiceImplTest {
    private Account account;
    private User user;
    private Operation operation;

    @Before
    public void setUp(){
        account = EntityBuilder.buildAccount(100L, "TEST", 100D, 0);
        user = EntityBuilder.buildUser(100L, "TEST", "TEST", account.getId(), "TEST", "TEST", 0);
        operation = EntityBuilder.buildOperation(100L, user.getId(), user.getAccountId(), 100D, "TEST", "01.01.01");
    }

    @After
    public void tearDown() throws Exception{
        account = null;
        user = null;
        operation = null;
    }

    @Test
    public void testGetInstance() throws Exception {
        AccountServiceImpl instance1 = AccountServiceImpl.getInstance();
        AccountServiceImpl instance2 = AccountServiceImpl.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testAdd() throws Exception {
        AccountServiceImpl.getInstance().add(account);
        Account actual = AccountServiceImpl.getInstance().getById(account.getId());
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(account, actual);
    }

    @Test
    public void testGetById() throws Exception {
        account = EntityBuilder.buildAccount(1L, "ADMIN", 0D, 0);
        Account actual = AccountServiceImpl.getInstance().getById(account.getId());
        Assert.assertEquals(account, actual);
    }

    @Test
    public void testAddFunds() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        user.setId(UserDaoImpl.getInstance().getMaxId());
        AccountServiceImpl.getInstance().addFunds(user, operation.getDescription(), operation.getAmount());
        Double expected = account.getDeposit() + operation.getAmount();
        Double actual = AccountDaoImpl.getInstance().getById(account.getId()).getDeposit();
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testBlockAccount() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        user.setId(UserDaoImpl.getInstance().getMaxId());
        AccountServiceImpl.getInstance().blockAccount(user, operation.getDescription());
        Integer expected = AccountStatusEnum.BLOCKED;
        Integer actual = AccountDaoImpl.getInstance().getById(account.getId()).getAccountStatusEnum();
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPayment() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        user.setId(UserDaoImpl.getInstance().getMaxId());
        AccountServiceImpl.getInstance().payment(user, operation.getDescription(), operation.getAmount());
        Double expected = account.getDeposit() - operation.getAmount();
        Double actual = AccountDaoImpl.getInstance().getById(account.getId()).getDeposit();
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(expected, actual, 0.01);
    }
}