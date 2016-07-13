package com.epam.moviemanagment.demonstration;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.epam.moviemanagment.domain.converter.EventConverter;
import com.epam.moviemanagment.domain.dto.Auditorium;
import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.Ticket;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.EventEntity;
import com.epam.moviemanagment.service.AuditoriumService;
import com.epam.moviemanagment.service.BookingService;
import com.epam.moviemanagment.service.EventService;
import com.epam.moviemanagment.service.UserService;
import com.epam.moviemanagment.service.discount.DiscountService;
import com.epam.moviemanagment.storage.EventStaticStorage;

/**
 * Class is responsible for demonstration of services work. Some scenarios were
 * simulated. See methods descriptions and comments at methods body for detailed
 * information.
 * 
 */
public class Demonstrator {

	public UserService userService;
	public AuditoriumService auditoriumService;
	public BookingService bookingService;
	public DiscountService discountService;
	public EventService eventService;

	/**
	 * Demonstrates auditoriumService work.
	 */
	public void demonstrateAuditoriumsViewing() {
		System.out.println('\n' + "Demonstration of auditoriums selection:");
		final Set<Auditorium> auditoriums = auditoriumService.getAll();

		for (final Auditorium auditorium : auditoriums) {
			System.out.println("Aud name: " + auditorium.getName() + "\n"
					+ "Number of seats: " + auditorium.getNumberOfSeats()
					+ "\n" + "Vip seats: " + auditorium.getVipSeats());
		}
	}

	/**
	 * Demonstrates bookingService work. bookingService is also used
	 * discountService and userService. For getting of event eventService is
	 * used
	 */
	public void demonstrateEventGettingAndPriceCalculating() {
		System.out.println('\n' + "Demonstration of price calculating:");

		// Preparing data for getting price
		// get some user
		final User user = this.userService
				.getUserByEmail("ivan_ivanov@epam.com");
		// get some event and date for this event
		final Event event = this.eventService.getByName("Terminator");
		final GregorianCalendar eventDate = new GregorianCalendar(2016, 7, 10, 15, 0);

		// Create set of seats, for which user wants to get total price with all
		// discounts
		final Set<Long> seatForBooking = new HashSet<>();
		seatForBooking.add(new Long(10)); // VIP seat
		seatForBooking.add(new Long(20)); // VIP seat
		seatForBooking.add(new Long(30)); // VIP seat
		seatForBooking.add(new Long(1)); // usual seat
		seatForBooking.add(new Long(2)); // usual seat
		seatForBooking.add(new Long(3)); // usual seat

		// Here price us calculating. Booking service is used discount service,
		// where discounts for user are calculated.
		final double totalPrice = this.bookingService.getTicketsPrice(event,
				eventDate, user, seatForBooking);
		System.out.println("Total price with discount: " + totalPrice);
	}

	/**
	 * Demonstration of tickets booking. Registered(has email) and Unregistered
	 * user(has no email) are booking tickets. Before and after booking
	 * operation number of booked tickets is shown. Also for registered user is
	 * shown number of his booked tickets before and after booking.
	 */
	public void demonstrateTicketsBooking() {
		System.out.println('\n' + "Demonstration of tickets booking:");

		// Just get event to find tickets for it
		final EventEntity eventEntity = EventStaticStorage.getEvents().get(0);
		final Event event = EventConverter.toDto(eventEntity);
		final GregorianCalendar eventDate = new GregorianCalendar(2016, 7, 10, 15, 0);

		// Check that at this moment we have 0 reserved tickets
		Set<Ticket> tickets = this.bookingService.getPurchasedTicketsForEvent(
				event, eventDate);
		System.out.println("Number of purchased tickets before booking: "
				+ tickets.size()); // 0 value must be showed here

		// Get available tickets for this event
		final Set<Ticket> availableTickets = this.bookingService
				.getAvailableTicketsForEvent(event, eventDate);

		// Get registered user and create unregistered user
		User registeredUser = this.userService
				.getUserByEmail("ivan_ivanov@epam.com");
		System.out
				.println("Count of bought tickets for registered user before booking: "
						+ registeredUser.getTickets().size());
		// Sign of unregistered user is absence of email.
		final User unregisteredUser = new User();
		unregisteredUser.setAdmin(false);
		unregisteredUser.setFirstName("UnregFn");
		unregisteredUser.setLastName("UnregLn");

		if (!availableTickets.isEmpty()) {
			final Iterator<Ticket> iterator = availableTickets.iterator();
			// Book ticket for unregistered user
			final Ticket bookedTicketByUnreg = iterator.next();
			bookedTicketByUnreg.setUser(unregisteredUser);

			// Book ticket for registered user
			final Ticket bookedTicketByReg = iterator.next();
			bookedTicketByReg.setUser(registeredUser);

			final Set<Ticket> bookedTickets = new HashSet<>();
			bookedTickets.add(bookedTicketByUnreg);
			bookedTickets.add(bookedTicketByReg);
			this.bookingService.bookTickets(bookedTickets);
		}

		// Check that now we have 1 purchased ticket
		tickets = bookingService.getPurchasedTicketsForEvent(event, eventDate);
		System.out.println("Number of purchased tickets after booking:  "
				+ tickets.size());

		// Also check that count of tickets for registered user was changed
		// after booking.
		registeredUser = this.userService
				.getUserByEmail("ivan_ivanov@epam.com");
		System.out
				.println("Count of bought tickets for registered user before booking: "
						+ registeredUser.getTickets().size());

	}

	/**
	 * Demonstrates userService work. User is created and than is saved.
	 */
	public void demonstrateUserCreation() {
		System.out.println('\n' + "Demonstration of user cetreation:");
		System.out.println("Number users before user creation : "
				+ this.userService.getAll().size());
		final User user = new User();
		user.setAdmin(true);
		user.setEmail("ivan_ivanov@epam.com");
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setId((long) 3);
		user.setTickets(new TreeSet<Ticket>());
		user.setBirthday(new GregorianCalendar(1990, 7, 10, 15, 0));
		this.userService.save(user);
		System.out.println("Number users after user creation :  "
				+ this.userService.getAll().size());

	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public void setAuditoriumService(final AuditoriumService auditoriumService) {
		this.auditoriumService = auditoriumService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}
}
