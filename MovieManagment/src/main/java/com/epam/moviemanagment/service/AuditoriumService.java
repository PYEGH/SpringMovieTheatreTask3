package com.epam.moviemanagment.service;

import java.util.Set;

import com.epam.moviemanagment.domain.dto.Auditorium;



public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     * 
     * @return set of all auditoriums
     */
    Set<Auditorium> getAll();

    /**
     * Finding auditorium by name
     * 
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    Auditorium getByName( String name);
    
    Auditorium getById( Long id);

}
