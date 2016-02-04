/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;
import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.Card;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class CardDaoImpl extends AbstractDao<Card> {
    private static CardDaoImpl instance;

    private CardDaoImpl(){}

    public static synchronized CardDaoImpl getInstance(){
        if(instance == null){
            instance = new CardDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Card> getAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getMaxId() throws DaoException {
        throw new UnsupportedOperationException();
    }

	    @Override
    public void add(Card entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Card getById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
