package com.epam.moviemanagment.service;

import com.epam.moviemanagment.domain.dto.Event;

public interface EventService extends AbstractDomainObjectService<Event> {

    /**
     * Finding event by name
     * 
     * @param name
     *            Name of the event
     * @return found event or <code>null</code>
     */
    Event getByName(String name);
}
