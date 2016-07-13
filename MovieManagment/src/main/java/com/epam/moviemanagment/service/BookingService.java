package com.epam.moviemanagment.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.Ticket;
import com.epam.moviemanagment.domain.dto.User;

public interface BookingService {

	/**
	 * Getting price when buying all supplied seats for particular event.
	 * 
	 * Detailed description from E-University practical task: User is needed to
	 * calculate discount. Event is needed to get base price, rating Vip seats
	 * should cost more than regular seats All prices for high rated movies
	 * should be higher.
	 * 
	 * @param event
	 *            Event to get base ticket price, vip seats and other
	 *            information
	 * @param dateTime
	 *            Date and time of event air
	 * @param user
	 *            User that buys ticket could be needed to calculate discount.
	 *            Can be <code>null</code>
	 * @param seats
	 *            Set of seat numbers that user wants to buy
	 * @return total price
	 */
	public double getTicketsPrice(Event event, Calendar dateTime, User user,
			Set<Long> seats);

	/**
	 * Books tickets in internal system. If user is not <code>null</code> in a
	 * ticket then booked tickets are saved with it
	 * 
	 * @param tickets
	 *            Set of tickets
	 */
	public void bookTickets(Set<Ticket> tickets);

	/**
	 * Method books selected by user tickets.
	 * 
	 * Detailed description from E-University practical task: Ticket should
	 * contain information about event, air dateTime, seat, and user. The user
	 * could be registered or not. If user is registered, then booking
	 * information is stored for that user (in the tickets collection).
	 * Purchased tickets for particular event should be stored.
	 * 
	 * @param event
	 *            Event to get tickets for
	 * @param dateTime
	 *            Date and time of airing of event
	 * @return set of all purchased tickets
	 */
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, Calendar dateTime);

	/**
	 * Getting all available tickets for event on specific air date and time
	 * 
	 * @param event
	 *            Event to get tickets for
	 * @param dateTime
	 *            Date and time of airing of event
	 * @return set of all purchased tickets
	 */
	public Set<Ticket> getAvailableTicketsForEvent(Event event, Calendar dateTime);

}
