/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IOperationDao;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.enums.ColumnName;
import by.pvt.khudnitsky.payments.enums.SqlRequest;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.ClosingUtil;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.utils.PaymentSystemLogger;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class OperationDaoImpl extends AbstractDao<Operation> implements IOperationDao{
    private static Logger logger = Logger.getLogger(OperationDaoImpl.class);
    private static OperationDaoImpl instance;
    private final String DELETE_BY_ACCOUNT_ID = "delete from Operation as o where o.account.id = :accountId";
    static String message;

    private OperationDaoImpl(){
        super(Operation.class);
    }

    public static synchronized OperationDaoImpl getInstance(){
        if(instance == null){
            instance = new OperationDaoImpl();
        }
        return instance;
    }

    @Override
    public void deleteByAccountId(Long id) throws DaoException {
        try {
            Session session = util.getSession();
            Query query = session.createQuery(DELETE_BY_ACCOUNT_ID);
            query.setParameter("accountId", id);
            query.executeUpdate();
        }
        catch(HibernateException e){
            message = "Unable to delete the operation. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
    }
}

