/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccountStatusEnum;

import java.util.Calendar;
import java.util.Set;

/**
 * Utility class for entities buildings
 * @author khudnitsky
 * @version 1.0
 */

public class EntityBuilder {
    private EntityBuilder(){}

    public static Account buildAccount(Double deposit, AccountStatusEnum accountStatus, Currency currency, User user, Set<Card> cards, Set<Operation> operations){
        Account account = new Account();
        account.setDeposit(deposit);
        account.setAccountStatus(accountStatus);
        account.setCurrency(currency);
        account.setUser(user);
        account.setCards(cards);
        account.setOperations(operations);
        return account;
    }

    public static Operation buildOperation(Double amount, String description, Calendar date, User user, Account account){
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setDate(date);
        operation.setUser(user);
        operation.setAccount(account);
        return operation;
    }

    public static User buildUser(String firstName, String lastName, String login, String password, UserDetail userDetail, Set<Account> accounts, Set<Operation> operations, Set<AccessLevel> accessLevels){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setUserDetail(userDetail);
        user.setAccounts(accounts);
        user.setOperations(operations);
        user.setAccessLevels(accessLevels);
        return user;
    }

    /**
     * Creates card
     * NOT REALISED
     * @return throw UnsupportedOperationException()
     */
    public static Card buildCard(){
        throw new UnsupportedOperationException();
    }
}
