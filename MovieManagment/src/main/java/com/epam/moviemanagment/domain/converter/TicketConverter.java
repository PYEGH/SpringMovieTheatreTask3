package com.epam.moviemanagment.domain.converter;


import java.util.Calendar;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.Ticket;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.EventEntity;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;

public class TicketConverter {
	public static Ticket toDto(TicketEntity entity) {
		final UserEntity userEntity = entity.getUserEntity();
		final EventEntity eventEntity = entity.getEventEntity();

		final User user = UserConverter.toDto(userEntity);
		final Event event = EventConverter.toDto(eventEntity);
		final Calendar dateTime = entity.getDateTime();
		final long seat = entity.getSeat();
		final Long id = new Long(entity.getId());

		return new Ticket(user, event, dateTime, seat, id);
	}

	public static TicketEntity toEntity(Ticket dto) {
		final User user = dto.getUser();
		final Event event = dto.getEvent();

		final UserEntity userEntity = UserConverter.toEntity(user);
		final EventEntity eventEntity = EventConverter.toEntity(event);
		final Calendar dateTime = dto.getDateTime();
		final long seat = dto.getSeat();
		final Long id = new Long(dto.getId());

		return new TicketEntity(userEntity, eventEntity, dateTime, seat, id);
	}
}
