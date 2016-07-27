package com.epam.moviemanagment.dao.ticket;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.dao.auditorium.AuditoriumDAO;
import com.epam.moviemanagment.dao.event.EventDAO;
import com.epam.moviemanagment.dao.user.UserDAO;
import com.epam.moviemanagment.dao.user.UserRowMapper;
import com.epam.moviemanagment.demonstration.Demonstrator;
import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;
import com.epam.moviemanagment.storage.TicketStorage;

@Component("TicketDBDAOBean")
@Scope("prototype")
public class TicketDBDao implements TicketDAO {
	
	private final Logger logger = Logger.getLogger(TicketDBDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private EventDAO eventDAO;
	
	
	private final static String SQL_INSERT_WITH_USER = "INSERT INTO tickets (ID, TICKET_DATE,SEAT,USER_ID ,EVENT_ID) values(?,?,?,?,?)";
	private final static String SQL_INSERT_BASIC = "INSERT INTO tickets (ID, TICKET_DATE,SEAT, EVENT_ID) values(?,?,?,?)";


	@Override
	public Set<TicketEntity> getPurchasedTicketsForEvent(Event event,
			Calendar dateTime) {
		List<TicketEntity> result = jdbcTemplate.query(
				"SELECT * FROM tickets WHERE EVENT_ID = ? and TICKET_DATE = ?", new TicketRowMapper(userDAO,eventDAO),
				new Object[] { event.getId(), new java.sql.Date(dateTime.getTimeInMillis()) });
		logger.info("getPurchasedTicketsForEvent result size: " + result.size());
		return new HashSet<TicketEntity>(result);
	}

	@Override
	public TicketEntity save(TicketEntity ticketEntity) {
		
		if(ticketEntity.getUserEntity() != null){
			final Long userId = ticketEntity.getUserEntity().getId();
			jdbcTemplate.update(SQL_INSERT_WITH_USER, 
					ticketEntity.getId().intValue(),
					ticketEntity.getDateTime(),
					ticketEntity.getSeat(),
					userId.intValue(),
					ticketEntity.getEventEntity().getId().intValue()
					);
		} else{
			jdbcTemplate.update(SQL_INSERT_BASIC, 
					ticketEntity.getId().intValue(),
					ticketEntity.getDateTime(),
					ticketEntity.getSeat(),
					ticketEntity.getEventEntity().getId().intValue()
					);
		}
		logger.info("DB dao: ticket was saved");
		return ticketEntity;
	}

	@Override
	public void remove(TicketEntity ticketEntity) {
		final String sqlDelete = "DELETE FROM tickets WHERE ID=?";
		jdbcTemplate.update(sqlDelete, ticketEntity.getId());
		logger.info("DB dao: ticketEntity was deleted");

	}

	@Override
	public TicketEntity getById(Long id) {
		List<TicketEntity> result = jdbcTemplate.query(
				"SELECT * FROM tickets WHERE tickets.id = ?", new TicketRowMapper(userDAO,eventDAO),
				new Object[] { id });		
		return result != null ? result.get(0) : null ;
	}

}
