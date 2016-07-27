package com.epam.moviemanagment.dao.event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.dao.auditorium.AuditoriumDAO;
import com.epam.moviemanagment.domain.EventRating;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.domain.entity.EventEntity;

public class EventRowMapper implements RowMapper<EventEntity> {

	private AuditoriumDAO auditoriumDAO;
		
	public EventRowMapper(AuditoriumDAO auditoriumDAO) {
		this.auditoriumDAO = auditoriumDAO;
	}

	@Override
	public EventEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		final int id = rs.getInt("ID");
		final String name = rs.getString("NAME");
		final Date date = rs.getDate("EVENT_DATE");
		final String rating = rs.getString("RATING");
		final int audId = rs.getInt("AUD_ID"); 
		final Double price = rs.getDouble("PRICE");
		
		
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final NavigableMap<Calendar, AuditoriumEntity> auditoriumEntities = new TreeMap<>();
		final AuditoriumEntity aud = auditoriumDAO.getById(new Long(audId));
		auditoriumEntities.put(cal, aud);
		
		NavigableSet<Calendar> airDates = new TreeSet<>();
		airDates.add(cal);
		
		final EventEntity eventEntity = new EventEntity();
		eventEntity.setId(new Long(id));
		eventEntity.setName(name);
		eventEntity.setAirDates(airDates);;
		eventEntity.setAuditoriums(auditoriumEntities);
		eventEntity.setBasePrice(price);
		eventEntity.setRating(EventRating.valueOf(rating));

		return eventEntity;
	}
}
