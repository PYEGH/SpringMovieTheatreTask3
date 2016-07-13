package com.epam.moviemanagment.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.epam.moviemanagment.dao.ticket.TicketDAO;
import com.epam.moviemanagment.domain.EventRating;
import com.epam.moviemanagment.domain.converter.TicketConverter;
import com.epam.moviemanagment.domain.dto.Auditorium;
import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.Ticket;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.service.discount.DiscountService;

public class BookingServiceImpl implements BookingService {
	private TicketDAO ticketDAO;
	private UserService userService;
	private DiscountService discountService;

	private final static double BASE_RATING_COEF = 1;
	private final static double HIGH_RATING_COEF = 1.5;
	private final static double LOW_RATING_COEF = 0.5;
	private final static double VIP_SEAT_COEF = 0.5;

	public BookingServiceImpl(final TicketDAO ticketDAO,
			final UserService userService, final DiscountService discountService) {
		this.ticketDAO = ticketDAO;
		this.userService = userService;
		this.discountService = discountService;
	}

	@Override
	public double getTicketsPrice(final Event event, final Calendar dateTime,
			final User user, final Set<Long> seats) {

		final double totalPrice = calculateTotalBasePriceForAuditorium(event,
				dateTime, seats);
		final double userDiscount = calculateUserDiscountFromDiscountStrategies(
				event, dateTime, user, seats);
		final double ratingCoef = calculatePriceCoficientFromEventRating(event);

		final double userDiscountCoef = 1 - userDiscount; // 1 - means 100%

		return totalPrice * ratingCoef * userDiscountCoef;
	}

	@Override
	public void bookTickets(final Set<Ticket> tickets) {
		for (final Ticket ticket : tickets) {
			final User user = ticket.getUser();

			// Ticket should contain information about:
			// event
			final boolean containsInfoAboutEvent = !ticket.getEvent().getName()
					.isEmpty();

			// air dateTime
			final boolean containsAirDate = !ticket.getEvent().getAirDates()
					.isEmpty();

			// seat
			final boolean containsSeat = new Long(ticket.getSeat()) != null;

			// user
			final boolean containsUser = user != null;
			if (containsInfoAboutEvent && containsAirDate && containsSeat
					&& containsUser) {
				// Sign of registered users: registered users have email.
				// So let's try to find user by email
				final User foundByEmailUser = this.userService
						.getUserByEmail(user.getEmail());
				final boolean isRegistredUser = foundByEmailUser != null;

				if (isRegistredUser) {
					// If user is registered, then booking information is stored
					// for that user
					// (in the tickets collection)
					foundByEmailUser.getTickets().add(ticket);
					this.userService.save(foundByEmailUser);
				}

				// Purchased tickets for particular event
				// should be stored
				final TicketEntity ticketEntity = TicketConverter
						.toEntity(ticket);
				this.ticketDAO.save(ticketEntity);
			}
		}
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(final Event event,
			final Calendar dateTime) {
		final Set<TicketEntity> ticketEntities = this.ticketDAO
				.getPurchasedTicketsForEvent(event, dateTime);
		final Set<Ticket> tickets = new TreeSet<>();
		for (final TicketEntity ticketEntity : ticketEntities) {
			if (ticketEntity.getUserEntity() != null) {
				final Ticket ticket = TicketConverter.toDto(ticketEntity);
				tickets.add(ticket);
			}
		}
		return tickets;
	}

	@Override
	public Set<Ticket> getAvailableTicketsForEvent(final Event event,
			final Calendar dateTime) {
		final Set<TicketEntity> ticketEntities = this.ticketDAO
				.getPurchasedTicketsForEvent(event, dateTime);
		final Set<Ticket> tickets = new TreeSet<>();
		for (final TicketEntity ticketEntity : ticketEntities) {
			if (ticketEntity.getUserEntity() == null) {
				final Ticket ticket = TicketConverter.toDto(ticketEntity);
				tickets.add(ticket);
			}
		}
		return tickets;
	}

	/**
	 * Calculate discount coefficient using all discount strategies.
	 * 
	 * @param event
	 * @param dateTime
	 * @param user
	 * @param seats
	 * @return
	 */
	private double calculateUserDiscountFromDiscountStrategies(
			final Event event, final Calendar dateTime, final User user,
			final Set<Long> seats) {
		final int numberOfTickets = seats.size();
		return this.discountService.getDiscount(user, event, dateTime,
				numberOfTickets);
	}

	/**
	 * Calculate price coefficient depending on event rating.
	 * 
	 * @param event
	 * @return
	 */
	private double calculatePriceCoficientFromEventRating(final Event event) {
		if (EventRating.HIGH == event.getRating()) {
			return HIGH_RATING_COEF;
		} else if (EventRating.LOW == event.getRating()) {
			return LOW_RATING_COEF;
		}
		return BASE_RATING_COEF;
	}

	/**
	 * Calculates total price for ticket at auditorium taking into account vip
	 * seat. For vip seats special coefficient is applied.
	 * 
	 * @param event
	 * @param dateTime
	 * @param seats
	 * @return
	 */
	private double calculateTotalBasePriceForAuditorium(final Event event,
			final Calendar dateTime, final Set<Long> seats) {
		// calculate price for booked tickets using VipSeats params
		final double eventBasePrice = event.getBasePrice();
		final Auditorium aud = event.getAuditoriums().get(dateTime);
		final Set<Long> audVipSeats = aud.getVipSeats();
		double totalPriceWithoutUserDiscount = 0;
		for (final Long seat : seats) {
			final double currentSeatPrice = audVipSeats.contains(seat) ? eventBasePrice
					* VIP_SEAT_COEF
					: eventBasePrice;
			totalPriceWithoutUserDiscount += currentSeatPrice;
		}
		return totalPriceWithoutUserDiscount;
	}
}
