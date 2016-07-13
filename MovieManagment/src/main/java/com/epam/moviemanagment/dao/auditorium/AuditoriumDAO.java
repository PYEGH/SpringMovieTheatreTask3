package com.epam.moviemanagment.dao.auditorium;

import java.util.Set;

import com.epam.moviemanagment.domain.entity.AuditoriumEntity;

public interface AuditoriumDAO {
	/**
	 * Getting all auditoriums from the system
	 * 
	 * @return set of all auditoriums
	 */
	public Set<AuditoriumEntity> getAll();

	/**
	 * Finding auditorium by name
	 * 
	 * @param name
	 *            Name of the auditorium
	 * @return found auditorium or <code>null</code>
	 */
	public AuditoriumEntity getByName(final String name);
}
