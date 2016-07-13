package com.epam.moviemanagment.dao.auditorium;

import java.util.Set;

import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.storage.AuditoriumStorage;

public class AuditoriumStaticDAO implements AuditoriumDAO {

	@Override
	public Set<AuditoriumEntity> getAll() {
		return AuditoriumStorage.getAuditoriums();
	}

	@Override
	public AuditoriumEntity getByName(final String name) {
		final Set<AuditoriumEntity> auditoriums = AuditoriumStorage
				.getAuditoriums();
		for (final AuditoriumEntity auditorium : auditoriums) {
			if(name.equals(auditorium.getName())){
				return auditorium;
			}
		}
		return null;
	}

}
