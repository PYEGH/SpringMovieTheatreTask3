package com.epam.moviemanagment.dao.auditorium;

import java.util.Set;

import com.epam.moviemanagment.domain.entity.AuditoriumEntity;

public interface AuditoriumDAO {
	/**
	 * Getting all auditoriums from the system
	 * 
	 * @return set of all auditoriums
	 */
	Set<AuditoriumEntity> getAll();

	/**
	 * Finding auditorium by name
	 * 
	 * @param name
	 *            Name of the auditorium
	 * @return found auditorium or <code>null</code>
	 */
	AuditoriumEntity getByName(String name);
}
