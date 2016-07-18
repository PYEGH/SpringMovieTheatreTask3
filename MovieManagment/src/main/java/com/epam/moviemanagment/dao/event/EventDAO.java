package com.epam.moviemanagment.dao.event;

import com.epam.moviemanagment.dao.AbstractDomainObjectDAO;
import com.epam.moviemanagment.domain.entity.EventEntity;

public interface EventDAO extends AbstractDomainObjectDAO<EventEntity> {
    /**
     * Finding event by name
     * 
     * @param name
     *            Name of the event
     * @return found event or <code>null</code>
     */
    EventEntity getByName(String name);
}
