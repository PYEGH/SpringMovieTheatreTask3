package com.epam.moviemanagment.demonstration;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.aspect.CounterAspect;
import com.epam.moviemanagment.dao.ticket.TicketDAO;
import com.epam.moviemanagment.domain.converter.EventConverter;
import com.epam.moviemanagment.domain.dto.Auditorium;
import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.Ticket;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.EventEntity;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.service.AuditoriumService;
import com.epam.moviemanagment.service.BookingService;
import com.epam.moviemanagment.service.EventService;
import com.epam.moviemanagment.service.UserService;
import com.epam.moviemanagment.service.discount.DiscountService;
import com.epam.moviemanagment.storage.EventStaticStorage;
import com.epam.moviemanagment.storage.TicketStorage;

/**
 * Class is responsible for demonstration of services work. Some scenarios were
 * simulated. See methods descriptions and comments at methods body for detailed
 * information.
 * 
 */
@Component("DemonstratorBean")
public class Demonstrator {
	private final Logger logger = Logger.getLogger(Demonstrator.class);

	@Autowired
	public UserService userService;
	@Autowired
	public AuditoriumService auditoriumService;
	@Autowired
	public BookingService bookingService;
	@Autowired
	public DiscountService discountService;
	@Autowired
	public EventService eventService;
	
	@Autowired
	public TicketDAO ticketDao;

	public Demonstrator(final UserService userService,
			final AuditoriumService auditoriumService,
			final BookingService bookingService,
			final DiscountService discountService,
			final EventService eventService, final TicketDAO ticketDao) {
		this.userService = userService;
		this.auditoriumService = auditoriumService;
		this.bookingService = bookingService;
		this.discountService = discountService;
		this.eventService = eventService;
		this.ticketDao = ticketDao;
	}

	
	public void demonstrateTicketDaoWork(){
		TicketEntity te = this.ticketDao.getById(new Long(1));
		
		logger.info(te == null ? "ticket was not found" : "ticket for event " + te.getEventEntity().getName());
		
		TicketEntity ticketForSave = TicketStorage.getTickets().get(19);
		this.ticketDao.save(ticketForSave);
		this.ticketDao.remove(ticketForSave);
		
		Event event = this.eventService.getById(new Long(1));		
		//this.ticketDao.getPurchasedTicketsForEvent(event, event.getAirDates().iterator().next());		
	}
	
	/**
	 * Demonstrates auditoriumService work.
	 */
	public void demonstrateAuditoriumsViewing() {
		logger.info('\n' + "Demonstration of auditoriums selection:");
		final Set<Auditorium> auditoriums = auditoriumService.getAll();

		for (final Auditorium auditorium : auditoriums) {
			logger.info("Aud name: " + auditorium.getName() + "\n"
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
		logger.info('\n' + "Demonstration of price calculating:");

		// Preparing data for getting price
		// get some user
		final User user = this.userService
				.getUserByEmail("ivan_ivanov@epam.com");
		// get some event and date for this event
		final Event event = this.eventService.getByName("Terminator");
		final GregorianCalendar eventDate = new GregorianCalendar(2016, 7, 10,
				15, 0);

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
		logger.info("Total price with discount: " + totalPrice);
	}
	
	
	public void demonstrateAuditoriumDBDao(){
		
		logger.info("Number of auditories: " + this.auditoriumService.getById(new Long(2)));
		logger.info("Number of auditories: " + this.auditoriumService.getAll().size());
		logger.info("Number of seats for auditoriy with name 'Aud2': " + this.auditoriumService.getByName("Aud2").getNumberOfSeats());
	}
	
	public void demonstrateEventDBDao(){
		final EventEntity eventEntity = EventStaticStorage.getEvents().get(0);
		final Event event = EventConverter.toDto(eventEntity);
		final GregorianCalendar eventDate = new GregorianCalendar(2016, 7, 10,
				15, 0);
		
		this.eventService.save(event);
		logger.info("Get by name: " + this.eventService.getByName(event.getName()).getName());
		logger.info("Get by id: " + this.eventService.getById(event.getId()).getName());
		this.eventService.remove(event);
		logger.info("All events size: " + this.eventService.getAll().size());
	}

	/**
	 * Demonstration of tickets booking. Registered(has email) and Unregistered
	 * user(has no email) are booking tickets. Before and after booking
	 * operation number of booked tickets is shown. Also for registered user is
	 * shown number of his booked tickets before and after booking.
	 */
	public void demonstrateTicketsBooking() {
		logger.info('\n' + "Demonstration of tickets booking:");

		// Just get event to find tickets for it
		final EventEntity eventEntity = EventStaticStorage.getEvents().get(0);
		final Event event = EventConverter.toDto(eventEntity);
		final GregorianCalendar eventDate = new GregorianCalendar(2016, 7, 10,
				15, 0);

		// Check that at this moment we have 0 reserved tickets
		Set<Ticket> tickets = this.bookingService.getPurchasedTicketsForEvent(
				event, eventDate);
		logger.info("Number of purchased tickets before booking: "
				+ tickets.size()); // 0 value must be showed here

		// Get available tickets for this event
		final Set<Ticket> availableTickets = this.bookingService
				.getAvailableTicketsForEvent(event, eventDate);

		// Get registered user and create unregistered user
		User registeredUser = this.userService
				.getUserByEmail("ivan_ivanov@epam.com");
		logger.info("Count of bought tickets for registered user before booking: "
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
		logger.info("Number of purchased tickets after booking:  "
				+ tickets.size());

		// Also check that count of tickets for registered user was changed
		// after booking.
		registeredUser = this.userService
				.getUserByEmail("ivan_ivanov@epam.com");
		logger.info("Count of bought tickets for registered user before booking: "
				+ registeredUser.getTickets().size());

	}

	/**
	 * Demonstrates userService work. User is created and than is saved.
	 */
	public void demonstrateUserCreation() {
		logger.info('\n' + "Demonstration of user cetreation:");
		logger.info("Number users before user creation : "
				+ this.userService.getAll().size());
		logger.info("Number users before user creation : "
				+ this.userService.getAll().size());
		final User user = new User();
		user.setAdmin(true);
		user.setEmail("ivan_ivanov123@epam.com");
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setId((long) 10);
		user.setTickets(new TreeSet<Ticket>());
		user.setBirthday(new GregorianCalendar(1990, 7, 10, 15, 0));
		this.userService.save(user);
		
		//logger.info("GetBy ID: " + this.userService.getById(new Long(6)).getFirstName());
		logger.info("GetBy Email: " + this.userService.getUserByEmail("ivan_ivanov123@epam.com").getEmail());
		
		
		logger.info("Number users after user creation :  "
				+ this.userService.getAll().size());
		
		logger.info("Number users after user creation :  "
				+ this.userService.getAll().size());
		
		this.userService.remove(user);
		logger.info("user by id: " + this.userService.getById(new Long(1)).getEmail());
	}
}
