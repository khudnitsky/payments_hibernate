/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.AbstractEntity;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

import java.util.List;

/**
 * Describes the interface <b>AbstractEntity</b>
 * @author khudnitsky
 * @version 1.0
 */
public interface IDao<T extends AbstractEntity> {
    void add(T entity) throws DaoException;
    List<T> getAll() throws DaoException;
    T getById(Long id) throws DaoException;
    void delete(Long id) throws DaoException;
    Long getMaxId() throws DaoException;
}
