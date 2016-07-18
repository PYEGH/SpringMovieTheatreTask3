package com.epam.moviemanagment.aspect;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.converter.TicketConverter;
import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.TicketEntity;


@Component("counterAspect")
@Aspect
public class CounterAspect {

	private final Logger logger = Logger.getLogger(CounterAspect.class);
	
	// For all maps key is event name
	private Map<String, Integer> eventByNameCounterMap = new HashMap<>();
	private Map<String, Integer> eventPriceCounterMap = new HashMap<>();
	private Map<String, Integer> counterTicketMap = new HashMap<>();

	@Pointcut("execution(* com.epam.moviemanagment.service.EventService.getByName(..))")
	private void getEventByNameMethod() {
	}

	@Pointcut("execution(* com.epam.moviemanagment.dao.ticket.TicketDAO.save(..))")
	private void getTicketDAOSaveMethod() {
	}

	@Pointcut("execution(* com.epam.moviemanagment.service.BookingService.getTicketsPrice(..))")
	private void getTicketsPriceMethod() {
	}

	/**
	 * Counts how many times every event was accessed by name
	 * 
	 * @param joinPoint
	 */
	@AfterReturning("getEventByNameMethod() && args(eventName)")
	public void countNumberOfGettingEventByName(JoinPoint joinPoint,
			String eventName) {

		if (!this.eventByNameCounterMap.containsKey(eventName)) {
			this.eventByNameCounterMap.put(eventName, 0);
		}
		this.eventByNameCounterMap.put(eventName,
				this.eventByNameCounterMap.get(eventName) + 1);

		logger.info("Counter for "
				+ joinPoint.getTarget().getClass().getSimpleName() + " "
				+ joinPoint.getSignature().getName() + " works. Event: "
				+ eventName);

	}

	/**
	 * Counts how many times each event prices were queried
	 * 
	 * @param joinPoint
	 */
	@AfterReturning("getTicketsPriceMethod() && args(event,dateTime,user,seats)")
	public void countNumberTicketPriceCalling(JoinPoint joinPoint,
			final Event event, Calendar dateTime, User user, Set<Long> seats) {
		logger.info("eventPrice " + event.getName() + " "
				+ event.getBasePrice());

		final String eventName = event.getName();
		if (!this.counterTicketMap.containsKey(eventName)) {
			this.counterTicketMap.put(eventName, 0);
		}
		this.counterTicketMap.put(eventName,
				this.counterTicketMap.get(eventName) + 1);
		logger.info("Counter for "
				+ joinPoint.getTarget().getClass().getSimpleName() + " "
				+ joinPoint.getSignature().getName() + " works. Event: "
				+ eventName);
	}

	/**
	 * Counts how many times event tickets were booked.
	 * 
	 * @param joinPoint
	 * @param ticketEntity
	 */
	@AfterReturning("getTicketDAOSaveMethod() && args(ticketEntity)")
	public void countNumberOfBookedTickets(JoinPoint joinPoint,
			TicketEntity ticketEntity) {
		logger.info("ticketEntity"
				+ ticketEntity.getEventEntity().getName());

		final String eventName = TicketConverter.toDto(ticketEntity).getEvent()
				.getName();
		if (!this.eventPriceCounterMap.containsKey(eventName)) {
			this.eventPriceCounterMap.put(eventName, 0);
		}
		this.eventPriceCounterMap.put(eventName,
				this.eventPriceCounterMap.get(eventName) + 1);

		logger.info("Counter for "
				+ joinPoint.getTarget().getClass().getSimpleName() + " "
				+ joinPoint.getSignature().getName() + " works. Event: "
				+ eventName);
	}
}
