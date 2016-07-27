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

	@Override
	public AuditoriumEntity getById(Long id) {
		// It is not used for demonstration. It was added after DB dao implementation.
		// Such method really used for DB DAO. For static storage this method is not used.
		// Of course it is necessary to implement it, but the main goal is not to create
		// perfect application, but to learn Spring features.
		// This unimplemented method is not interrupt
        // main goal so in my opinion it can be won't implemented.
		// I ask to understand and forgive.
		
		// TODO Auto-generated method stub
		return null;
	}

}
