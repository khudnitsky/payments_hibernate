package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserDaoImplTest {
    private OperationDaoImpl operationDao = OperationDaoImpl.getInstance();
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private AccountDaoImpl accountDao = AccountDaoImpl.getInstance();
    private UserDetailDaoImpl userDetailDao = UserDetailDaoImpl.getInstance();
    private CurrencyDaoImpl currencyDao = CurrencyDaoImpl.getInstance();
    private AccessLevelDaoImpl accessLevelDao = AccessLevelDaoImpl.getInstance();
    private User expectedUser;
    private User actualUser;
    private Operation operation;
    private UserDetail userDetail;
    private Account account;
    private Currency currency;
    private AccessLevel accessLevel;
    private Serializable operationId;
    private Serializable accountId;
    private Serializable userId;
    private Serializable currencyId;
    private Serializable accessLevelId;

    @Before
    public void setUp() throws Exception {
        accessLevel = EntityBuilder.buildAccessLevel(AccessLevelType.CLIENT);
        Set<AccessLevel> accessLevels = new HashSet<>();
        accessLevels.add(accessLevel);

        Address address = EntityBuilder.buildAddress("TEST", "TEST", "TEST");
        userDetail = EntityBuilder.buildUserDetail(address);
        expectedUser = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", userDetail);
        Set<User> users = new HashSet<>();
        users.add(expectedUser);

        expectedUser.setAccessLevels(accessLevels);
        accessLevel.setUsers(users);

        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        account = EntityBuilder.buildAccount(200D, AccountStatusType.UNBLOCKED, currency, expectedUser);
        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        expectedUser.setAccounts(accounts);

        operation = EntityBuilder.buildOperation(200D, "TEST", Calendar.getInstance(), expectedUser, account);
        Set<Operation> operations = new HashSet<>();
        expectedUser.setOperations(operations);
    }

    @Test
    public void testSave() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        actualUser = userDao.getById((Long) userId);
        Assert.assertEquals("save() method failed: ", expectedUser, actualUser);
        delete();
    }

    @Test
    public void testGetAll() throws Exception {
        Long expectedSize = (long) userDao.getAll().size();
        Long actualSize = userDao.getAmount(); // todo не будет работать, будет находить всех, переделать
        Assert.assertEquals("getAll() method failed: ", expectedSize, actualSize);
    }

    @Test
    public void testGetById() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        actualUser = userDao.getById((Long) userId);
        Assert.assertEquals("getById() method failed: ", expectedUser, actualUser);
        delete();
    }


    @Test
    public void testUpdate() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        expectedUser.setFirstName("UPDATED");
        userDao.update(expectedUser);
        actualUser = userDao.getById((Long) userId);
        Assert.assertEquals("update() method failed: ", expectedUser, actualUser);
        delete();
    }

    @Test
    public void testDelete() throws Exception {
        persistEntities();
        delete();
        actualUser = userDao.getById((Long) userId);
        Assert.assertNull("delete() method failed: ", actualUser);
    }

    @After
    public void tearDown() throws Exception{
        expectedUser = null;
        actualUser = null;
        account = null;
        operation = null;
        accessLevel = null;
        userDetail = null;
        currency = null;
        operationId = null;
        accountId = null;
        userId = null;
        currencyId = null;
        accessLevelId = null;
    }

    private void persistEntities() throws Exception {
        userDetailDao.save(userDetail);
        accessLevelDao.save(accessLevel);
        currencyId = currencyDao.save(currency);
        userId = userDao.save(expectedUser);
        accountDao.save(account);
        operationId = operationDao.save(operation);
    }

    private void delete() throws by.pvt.khudnitsky.payments.exceptions.DaoException {
        operationDao.delete((Long) operationId);
        userDao.delete((Long) userId);
        currencyDao.delete((Long) currencyId);
    }

}