package by.pvt.khudnitsky.payments.entities;

import by.pvt.khudnitsky.payments.enums.CurrencyEnum;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 17:16
 */
@Entity
public class Currency extends AbstractEntity{
    private static final long serialVersionUID = 9L;

    public CurrencyEnum getCurrency() {
        return currency;
    }
    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }
    private CurrencyEnum currency;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    private Set<Account> accounts;

    public Currency() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        if (!super.equals(o)) return false;

        Currency currency1 = (Currency) o;

        return currency == currency1.currency;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currency=" + currency +
                '}';
    }
}
