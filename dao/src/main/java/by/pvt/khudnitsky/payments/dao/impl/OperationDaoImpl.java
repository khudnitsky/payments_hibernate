/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.enums.ColumnName;
import by.pvt.khudnitsky.payments.enums.SqlRequest;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.ClosingUtil;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.utils.PaymentSystemLogger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class OperationDaoImpl extends AbstractDao<Operation> {
    private static OperationDaoImpl instance;

    private OperationDaoImpl(){
        super(Operation.class);
    }

    public static synchronized OperationDaoImpl getInstance(){
        if(instance == null){
            instance = new OperationDaoImpl();
        }
        return instance;
    }
}

