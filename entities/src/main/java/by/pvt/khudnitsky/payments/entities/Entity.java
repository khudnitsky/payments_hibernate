/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

import java.io.Serializable;

/**
 * Describes the abstract entity <b>Entity</b>
 * @author khudnitsky
 * @version 1.0
 *
 */
public abstract class Entity implements Serializable{
    private static final long serialVersionUID = 1L;
    protected Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        return id != null ? id.equals(entity.id) : entity.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + "]";
    }

    /**
     * Creates new entity </b>
     */
    public Entity() {}

    /**
     * Creates new entity </b>
     */
    public Entity(Long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
