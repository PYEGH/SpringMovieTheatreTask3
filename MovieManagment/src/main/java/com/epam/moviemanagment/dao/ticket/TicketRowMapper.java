package com.epam.moviemanagment.dao.ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.epam.moviemanagment.dao.auditorium.AuditoriumDAO;
import com.epam.moviemanagment.dao.event.EventDAO;
import com.epam.moviemanagment.dao.user.UserDAO;
import com.epam.moviemanagment.domain.entity.EventEntity;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;


public class TicketRowMapper implements RowMapper<TicketEntity>{

	private UserDAO userDAO;
	private EventDAO eventDAO;
	
	
	
	public TicketRowMapper(UserDAO userDAO, EventDAO eventDAO) {
		this.userDAO = userDAO;
		this.eventDAO = eventDAO;
	}



	@Override
	public TicketEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
    	final int id = rs.getInt("ID");
    	final Date date = rs.getDate("TICKET_DATE");
    	final int seat = rs.getInt("SEAT");    	
    	final int userId = rs.getInt("USER_ID");
    	final int eventId = rs.getInt("EVENT_ID"); 
		
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		final UserEntity user = userDAO.getById(new Long(userId));
		final EventEntity event = eventDAO.getById(new Long(eventId));
    	
        return new TicketEntity(user, event, cal, seat, new Long(id));
	}

}
