package com.epam.moviemanagment.domain.converter;

import java.util.NavigableSet;
import java.util.TreeSet;

import com.epam.moviemanagment.domain.dto.Ticket;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;

public class UserConverter {

	public static User toDto(UserEntity entity) {
		final User dto = new User();
		if(entity != null){
			dto.setAdmin(entity.isAdmin());
			dto.setEmail(entity.getEmail());
			dto.setFirstName(entity.getFirstName());
			dto.setLastName(entity.getLastName());
			dto.setId(entity.getId());
			dto.setBirthday(entity.getBirthday());

			final NavigableSet<TicketEntity> ticketEntities = entity.getTickets();
			final NavigableSet<Ticket> ticketDtos = new TreeSet<>();
			for (final TicketEntity ticketEntity : ticketEntities) {
				final Ticket ticketDto = TicketConverter.toDto(ticketEntity);
				ticketDtos.add(ticketDto);
			}
			dto.setTickets(ticketDtos);
		}
		return dto;
	}

	public static UserEntity toEntity(User dto) {
		final UserEntity entity = new UserEntity();
		if(dto != null){
			entity.setAdmin(dto.isAdmin());
			entity.setEmail(dto.getEmail());
			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			entity.setId(dto.getId());
			entity.setBirthday(dto.getBirthday());

			final NavigableSet<Ticket> ticketDtos = dto.getTickets();
			final NavigableSet<TicketEntity> ticketEntities = new TreeSet<>();
			for (final Ticket ticket : ticketDtos) {
				final TicketEntity ticketEntity = TicketConverter.toEntity(ticket);
				ticketEntities.add(ticketEntity);
			}
			entity.setTickets(ticketEntities);
		}		
		return entity;
	}

}
