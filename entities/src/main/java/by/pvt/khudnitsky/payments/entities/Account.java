/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

import by.pvt.khudnitsky.payments.constants.AccountStatus;

import javax.persistence.*;
import java.util.Set;

/**
 * Describes the entity <b>Account</b>
 * @author khudnitsky
 * @version 1.0
 *
 */

@Entity
public class Account extends AbstractEntity {
    private static final long serialVersionUID = 3L;
    private User user;
    private String currency;
    private Double deposit;
    private AccountStatus accountStatus;
    private Set<Card> cards;
    private Set<Operation> operations;

    public Account() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (currency != null ? !currency.equals(account.currency) : account.currency != null) return false;
        if (deposit != null ? !deposit.equals(account.deposit) : account.deposit != null) return false;
        return accountStatus != null ? accountStatus.equals(account.accountStatus) : account.accountStatus == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (deposit != null ? deposit.hashCode() : 0);
        result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account [currency=" + currency + ", amount=" + deposit + ", status=" + accountStatus + "]";
    }

    /**
     * @return the currency
     */
    @Column(nullable = false, length = 3)
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the amount
     */
    @Column(nullable = false, precision = 2)
    public Double getDeposit() {
        return deposit;
    }

    /**
     * @param amount the amount to set
     */
    public void setDeposit(Double amount) {
        this.deposit = amount;
    }

    /**
     * @return the status
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('UNBLOCKED', 'BLOCKED')")
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    /**
     * @param status the status to set
     */
    public void setAccountStatus(AccountStatus status) {
        this.accountStatus = status;
    }

    /**
     * @return user
     */
    @ManyToOne
    @JoinColumn(name = "F_USER_ID")
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user - entity of <b>User</b>
     */
    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
