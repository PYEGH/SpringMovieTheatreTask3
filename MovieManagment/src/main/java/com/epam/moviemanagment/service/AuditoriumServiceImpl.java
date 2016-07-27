package com.epam.moviemanagment.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.dao.auditorium.AuditoriumDAO;
import com.epam.moviemanagment.dao.event.EventDAO;
import com.epam.moviemanagment.dao.user.UserDAO;
import com.epam.moviemanagment.domain.converter.AuditoriumConverter;
import com.epam.moviemanagment.domain.dto.Auditorium;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;

@Component("AuditoriumServiceBean")
@Scope("prototype")
public class AuditoriumServiceImpl implements AuditoriumService {

	@Autowired
	private AuditoriumDAO auditoriumDAO;

	public AuditoriumServiceImpl(final AuditoriumDAO auditoriumDAO) {
		this.auditoriumDAO = auditoriumDAO;
	}

	@Override
	public Set<Auditorium> getAll() {
		final Set<AuditoriumEntity> auditoriumEntities = this.auditoriumDAO
				.getAll();
		final Set<Auditorium> auditoriums = new HashSet<>();
		for (final AuditoriumEntity auditoriumEntity : auditoriumEntities) {
			final Auditorium auditorium = AuditoriumConverter
					.toDto(auditoriumEntity);
			auditoriums.add(auditorium);
		}
		return auditoriums;
	}

	@Override
	public Auditorium getByName(final String name) {
		final AuditoriumEntity auditoriumEntity = this.auditoriumDAO
				.getByName(name);
		return AuditoriumConverter.toDto(auditoriumEntity);
	}

	@Override
	public Auditorium getById(Long id) {
		final AuditoriumEntity auditoriumEntity = this.auditoriumDAO
				.getById(id);
		return AuditoriumConverter.toDto(auditoriumEntity);
	}
}
