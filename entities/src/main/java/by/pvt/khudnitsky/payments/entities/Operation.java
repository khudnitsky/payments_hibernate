/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Describes the entity <b>Operation</b>
 * @author khudnitsky
 * @version 1.0
 *
 */

@Entity
public class Operation extends AbstractEntity {
    private static final long serialVersionUID = 4L;

    private User user;
    private Account account;
    private Double amount;
    private String description;
    private Calendar date;

    public Operation() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        if (!super.equals(o)) return false;

        Operation operation = (Operation) o;

        if (user != null ? !user.equals(operation.user) : operation.user != null) return false;
        if (account != null ? !account.equals(operation.account) : operation.account != null) return false;
        if (amount != null ? !amount.equals(operation.amount) : operation.amount != null) return false;
        if (description != null ? !description.equals(operation.description) : operation.description != null)
            return false;
        return date != null ? date.equals(operation.date) : operation.date == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "user=" + user +
                ", account=" + account +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "F_USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "F_ACCOUNT_ID")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the amount
     */
    @Column(nullable = false, precision = 2)
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the description
     */
    @Column(nullable = false, length = 50)
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(value = TemporalType.TIMESTAMP) // TODO разобраться
    @Column(nullable = false)
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}