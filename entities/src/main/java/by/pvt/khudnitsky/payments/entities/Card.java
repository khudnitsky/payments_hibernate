/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

/**
 * Describes the entity <b>Card</b>
 * @author khudnitsky
 * @version 1.0
 *
 */
public class Card extends Entity{
    private static final long serialVersionUID = 1L;
    private Long accountId;
    private String validity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        if (!super.equals(o)) return false;

        Card card = (Card) o;

        if (accountId != null ? !accountId.equals(card.accountId) : card.accountId != null) return false;
        return validity != null ? validity.equals(card.validity) : card.validity == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (validity != null ? validity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card [accountId=" + accountId + ", validity=" + validity + "]";
    }

    /**
     * @return the accountId
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the validity
     */
    public String getValidity() {
        return validity;
    }

    /**
     * @param validity the validity to set
     */
    public void setValidity(String validity) {
        this.validity = validity;
    }
}
