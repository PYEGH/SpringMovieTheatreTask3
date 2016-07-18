package com.epam.moviemanagment.dao.ticket;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.storage.TicketStorage;

public interface TicketDAO {
	Set<TicketEntity> getPurchasedTicketsForEvent(Event event,
			Calendar dateTime);

	TicketEntity save(TicketEntity ticketEntity);

	void remove(TicketEntity ticketEntity);

	TicketEntity getById(Long id);
}
