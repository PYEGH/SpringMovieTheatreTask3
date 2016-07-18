package com.epam.moviemanagment.dao.event;

import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.entity.EventEntity;
import com.epam.moviemanagment.storage.EventStaticStorage;

@Component("EventStaticDAOBean")
@Scope("prototype")
public class EventStaticDAO implements EventDAO{

	@Override
	public EventEntity save(final EventEntity event) {
		EventStaticStorage.getEvents().add(event);
		return event;
	}

	@Override
	public void remove(EventEntity event) {
		EventStaticStorage.getEvents().remove(event);		
	}

	@Override
	public EventEntity getById(Long id) {
		final List<EventEntity> events = EventStaticStorage.getEvents();
		for (final EventEntity event : events) {
			if (id.equals(event.getId())) {
				return event;
			}
		}
		return null;
	}

	@Override
	public Collection<EventEntity> getAll() {
		return EventStaticStorage.getEvents();
	}

	@Override
	public EventEntity getByName(final String name) {
		final List<EventEntity> events = EventStaticStorage.getEvents();
		for (final EventEntity event : events) {
			if (name.equals(event.getName())) {
				return event;
			}
		}
		return null;	
	}
}
