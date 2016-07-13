package com.epam.moviemanagment.dao.ticket;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.storage.TicketStorage;

public interface TicketDAO {
	public Set<TicketEntity> getPurchasedTicketsForEvent(Event event,
			Calendar dateTime);

	public TicketEntity save(final TicketEntity ticketEntity);

	public void remove(final TicketEntity ticketEntity);

	public TicketEntity getById(final Long id);
}
