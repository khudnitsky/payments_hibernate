package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.entities.AbstractEntity;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;

import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public interface IService<T extends AbstractEntity> {
    /**
     * Calls Dao save() method
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    void add(T entity) throws SQLException, ServiceException;

    /**
     *  Calls Dao getAll() method
     * @return list of objects of derived class AbstractEntity
     * @throws SQLException
     */
    List<T> getAll() throws SQLException, ServiceException;

    /**
     * Calls Dao getById() method
     * @param id - id of entity
     * @return object of derived class AbstractEntity
     * @throws SQLException
     */
    T getById(Long id) throws SQLException, ServiceException;

    /**
     * Calls Dao update() method
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    void update(T entity) throws SQLException, ServiceException;

    /**
     * Calls Dao delete() method
     * @param id - id of entity
     * @throws SQLException
     */
    void delete(Long id) throws SQLException, ServiceException;
}
