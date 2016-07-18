package com.epam.moviemanagment.dao.ticket;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.Ticket;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;
import com.epam.moviemanagment.storage.TicketStorage;
import com.epam.moviemanagment.storage.UserStaticStorage;

@Component("TicketStaticDAOBean")
@Scope("prototype")
public class TicketStaticDAO implements TicketDAO {

	@Override
	public Set<TicketEntity> getPurchasedTicketsForEvent(Event event, Calendar dateTime) {
		final Set<TicketEntity> result = new HashSet<>();
		final List<TicketEntity> ticketEntities = TicketStorage.getTickets();
		for(final TicketEntity ticketEntity : ticketEntities){
			//final boolean eventExists = ticketEntity.getEventEntity().equals(event);
			final boolean eventExists = ticketEntity.getEventEntity().getName().equals(event.getName());
			//TODO check correctness of comparison of dates
			final boolean timeExists = ticketEntity.getDateTime().equals(dateTime);
			if(eventExists && timeExists){
				result.add(ticketEntity);
			}
		}
		return result;
	}

	@Override
	public TicketEntity save(TicketEntity ticketEntity) {
		final TicketEntity foundTicketEntity = getById(ticketEntity.getId());
		if (foundTicketEntity != null) {
			remove(foundTicketEntity);
		}
		TicketStorage.getTickets().add(ticketEntity);
		return ticketEntity;
	}
	
	@Override
	public void remove(final TicketEntity ticketEntity) {
		TicketStorage.getTickets().remove(ticketEntity);
	}

	@Override
	public TicketEntity getById(final Long id) {
		final List<TicketEntity> ticketEntities = TicketStorage.getTickets();
		for (final TicketEntity ticketEntity : ticketEntities) {
			if (id.equals(ticketEntity.getId())) {
				return ticketEntity;
			}
		}
		return null;
	}

}
