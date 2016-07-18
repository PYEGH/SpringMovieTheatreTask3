package com.epam.moviemanagment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.dao.event.EventDAO;
import com.epam.moviemanagment.dao.user.UserDAO;
import com.epam.moviemanagment.domain.converter.EventConverter;
import com.epam.moviemanagment.domain.converter.UserConverter;
import com.epam.moviemanagment.domain.dto.Auditorium;
import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.EventEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;

@Component("EventServiceBean")
@Scope("prototype")
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDAO eventDao;

	public EventServiceImpl(final EventDAO eventDao) {
		this.eventDao = eventDao;
	}

	@Override
	public Event save(final Event eventDto) {
		final EventEntity entity = EventConverter.toEntity(eventDto);
		this.eventDao.save(entity);
		return eventDto;
	}

	@Override
	public void remove(final Event eventDto) {
		final EventEntity entity = EventConverter.toEntity(eventDto);
		this.eventDao.remove(entity);
	}

	@Override
	public Event getById(final Long id) {
		final EventEntity entity = this.eventDao.getById(id);
		return EventConverter.toDto(entity);
	}

	@Override
	public Collection<Event> getAll() {
		final List<Event> events = new ArrayList<>();
		final List<EventEntity> eventEntities = (List<EventEntity>) this.eventDao
				.getAll();
		for (final EventEntity eventEntity : eventEntities) {
			final Event event = EventConverter.toDto(eventEntity);
			events.add(event);
		}
		return events;
	}

	@Override
	public Event getByName(final String name) {
		final EventEntity eventEntity = this.eventDao.getByName(name);
		if (eventEntity != null) {
			return EventConverter.toDto(eventEntity);
		}
		return null;
	}

}
