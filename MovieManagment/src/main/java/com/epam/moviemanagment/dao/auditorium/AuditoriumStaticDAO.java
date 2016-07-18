package com.epam.moviemanagment.dao.auditorium;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.storage.AuditoriumStorage;

@Component("AuditoriumStaticDAOBean")
@Scope("prototype")
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
