/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

import by.pvt.khudnitsky.payments.constants.AccessLevel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Set;

/**
 * Describes the entity <b>User</b>
 * @author khudnitsky
 * @version 1.0
 */

@Entity
@SQLDelete(sql = "UPDATE T_EMPLOYEE F_STATUS SET F_STATUS = 'deleted' WHERE F_ID = ?" )  // TODO to do deleting
@OnDelete(action = OnDeleteAction.CASCADE)
public class User extends AbstractEntity {
    private static final long serialVersionUID = 2L;

    private String firstName;
    private String lastName;
    private String login;
    private String password;       // TODO Шифрование
    private Set<Account> accounts;
    private Set<Operation> operations;
    private AccessLevel accessLevel;

    public User() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (accounts != null ? !accounts.equals(user.accounts) : user.accounts != null) return false;
        return accessLevel == user.accessLevel;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (accessLevel != null ? accessLevel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", accounts=" + accounts +
                ", accessLevel=" + accessLevel +
                '}';
    }

    /**
     * @return the firstName
     */
    @Column(nullable = false, length = 15)
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    @Column(nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the login
     */
    @Column(unique = true, nullable = false, length = 25)
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    @Column(nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the set of accounts
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Account> getAccounts() {
        return accounts;
    }

    /**
     *
     * @param accounts the set of accounts
     */
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the accessLevel
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('CLIENT', 'ADMINISTRATOR')")
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    /**
     * @param accessLevel the accessLevel to set
     */
    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
