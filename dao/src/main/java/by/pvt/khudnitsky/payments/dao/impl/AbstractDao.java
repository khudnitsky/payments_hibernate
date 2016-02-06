/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.intrf.IDao;
import by.pvt.khudnitsky.payments.entities.AbstractEntity;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Describes the abstract class <b>AbstractDao</b>
 * @param <T> - entity of <b>AbstractEntity</b>
 * @author khudnitsky
 * @version 1.0
 */

public abstract class AbstractDao<T extends AbstractEntity> implements IDao<T> {
    private static Logger logger = Logger.getLogger(AbstractDao.class);
    protected static HibernateUtil util = HibernateUtil.getInstance();
    protected Transaction transaction;
    private Class persistentClass;

    protected AbstractDao(Class persistentClass){
        this.persistentClass = persistentClass;
    }

    @Override
    public Serializable save(T entity) throws DaoException{
        Serializable id;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            session.update(entity);
            id = session.getIdentifier(entity);
            transaction.commit();
        }
        catch(HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            transaction.rollback();
            throw new DaoException();
        }
        return id;
    }

    @Override
    public void delete(Long id) throws DaoException{
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            T entity = (T) session.get(persistentClass, id);
            session.delete(entity);
            transaction.commit();
        }
        catch(HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            transaction.rollback();
            throw new DaoException(e.getMessage());
        }
        catch(IllegalArgumentException e){
            logger.error("Error was thrown in DAO: " + e);
            transaction.rollback();
            throw new DaoException();
        }
    }

    @Override
    public void update(T entity) throws DaoException{
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
        catch(HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            transaction.rollback();
            throw new DaoException();
        }
    }

    @Override
    public T getById(Long id) throws DaoException{
        T entity;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            entity = (T)session.get(persistentClass, id);
            transaction.commit();
        }
        catch(HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            transaction.rollback();
            throw new DaoException();
        }
        return entity;
    }

    @Override
    public List<T> getAll() throws DaoException {
        List<T> results;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(persistentClass);
            results = criteria.list();
            transaction.commit();
        }
        catch(HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            transaction.rollback();
            throw new DaoException();
        }
        return results;
    }
}
