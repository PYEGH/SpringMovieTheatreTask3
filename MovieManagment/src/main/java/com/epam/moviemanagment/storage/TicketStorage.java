package com.epam.moviemanagment.storage;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.epam.moviemanagment.domain.entity.EventEntity;
import com.epam.moviemanagment.domain.entity.TicketEntity;

/**
 * Class is simulate storage for Tickets.
 * 
 */
public class TicketStorage {
	private static List<TicketEntity> tickets = new ArrayList<>();

	static {

		for (int i = 0; i < 50; i++) {
			for (final EventEntity eventEntity : EventStaticStorage.getEvents()) {
				final TicketEntity ticketEntity = new TicketEntity(null,
						eventEntity, new GregorianCalendar(2016, 7, 10, 15, 0),
						i, new Long(i));
				tickets.add(ticketEntity);
			}
		}
	}

	public static List<TicketEntity> getTickets() {
		return tickets;
	}

	public static void setTickets(List<TicketEntity> tickets) {
		TicketStorage.tickets = tickets;
	}

}
