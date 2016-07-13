package com.epam.moviemanagment.storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.epam.moviemanagment.domain.EventRating;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.domain.entity.EventEntity;

/**
 * Class is simulate storage for Event.
 *
 */
public class EventStaticStorage {
	private static List<EventEntity> events = new ArrayList<>();

	static {
		// iterator contains just two elements
		final Iterator<AuditoriumEntity> iterator = AuditoriumStorage.getAuditoriums().iterator();
		final AuditoriumEntity auditorium1 = iterator.next();
		final AuditoriumEntity auditorium2 = iterator.next();
		
		// event 1
		final NavigableMap<Calendar, AuditoriumEntity> auditoriumAndDate1 = new TreeMap<>();
		final NavigableSet<Calendar> airDates1 = new TreeSet<>(); 
		//setting of air dates for the 1st event
		final Calendar date1 = new GregorianCalendar(2016, 7, 10, 15, 0);
		final Calendar date2 = new GregorianCalendar(2016, 7, 10, 19, 0);
		airDates1.add(date1);
		airDates1.add(date2);		

		// adding dates for auditoriums for the 1st event
		auditoriumAndDate1.put(date1, auditorium1);
		auditoriumAndDate1.put(date2, auditorium1);
		auditoriumAndDate1.put(date1, auditorium2);
		auditoriumAndDate1.put(date2, auditorium2);


		final EventEntity event1 = new EventEntity();
		event1.setId((long) 1);
		event1.setName("Terminator");
		event1.setRating(EventRating.MID);
		event1.setBasePrice((double) 10.0);
		event1.setAirDates(airDates1);
		event1.setAuditoriums(auditoriumAndDate1);


		// event 2
		final NavigableMap<Calendar, AuditoriumEntity> auditoriumAndDate2 = new TreeMap<>();
		final NavigableSet<Calendar> airDates2 = new TreeSet<>(); 
		//seetting of air dates for the 2nd event
		final Calendar date3 = new GregorianCalendar(2016, 8, 10, 15, 0);
		final Calendar date4 = new GregorianCalendar(2016, 8, 10, 19, 0);
		airDates2.add(date3);
		airDates2.add(date4);
		
		// adding dates for auditoriums for the 2nd event
		auditoriumAndDate2.put(date3, auditorium1);
		auditoriumAndDate2.put(date4, auditorium1);
		auditoriumAndDate2.put(date3, auditorium2);
		auditoriumAndDate2.put(date4, auditorium2);
		
		final EventEntity event2 = new EventEntity();
		event2.setId((long) 2);
		event2.setName("Terminator2");
		event2.setRating(EventRating.HIGH);
		event2.setBasePrice((double) 20.0);
		event2.setAirDates(airDates2);
		event2.setAuditoriums(auditoriumAndDate2);
		
		events.add(event1);
		events.add(event2);
	}

	public static List<EventEntity> getEvents() {
		return events;
	}

	public static void setEvents(List<EventEntity> events) {
		EventStaticStorage.events = events;
	}
}
