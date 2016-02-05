package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.entities.AbstractEntity;

import java.sql.Connection;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public abstract class AbstractService<T extends AbstractEntity> implements IService<T>{
    protected Connection connection;
}
