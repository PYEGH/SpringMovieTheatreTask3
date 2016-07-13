package com.epam.moviemanagment.dao;


import java.util.Collection;

import com.epam.moviemanagment.domain.entity.DomainObjectEntity;


public interface AbstractDomainObjectDAO<T extends DomainObjectEntity> {

    /**
     * Saving new object to storage or updating existing one
     * 
     * @param object
     *            Object to save
     * @return saved object with assigned id
     */
    public T save(T object);

    /**
     * Removing object from storage
     * 
     * @param object
     *            Object to remove
     */
    public void remove(T object);

    /**
     * Getting object by id from storage
     * 
     * @param id
     *            id of the object
     * @return Found object or <code>null</code>
     */
    public T getById(Long id);

    /**
     * Getting all objects from storage
     * 
     * @return collection of objects
     */
    public  Collection<T> getAll();
}
