package com.epam.moviemanagment.dao.event;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.dao.auditorium.AuditoriumDAO;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.domain.entity.EventEntity;

@Component("EventDBDAOBean")
@Scope("prototype")
public class EventDBDao implements EventDAO {

	private final Logger logger = Logger.getLogger(EventDBDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AuditoriumDAO auditoriumDAO;

	@Override
	public EventEntity save(EventEntity object) {

		final String sqlInsert = "INSERT INTO events (ID,NAME,EVENT_DATE,RATING,PRICE,AUD_ID) values (?,?,?,?,?,?)";

		// first date will be saved.
		final Calendar calDate = object.getAirDates().iterator().next();

		Calendar cal = object.getAuditoriums().keySet().iterator().next();
		AuditoriumEntity aud = object.getAuditoriums().get(cal);

		jdbcTemplate.update(sqlInsert, 
				object.getId().intValue(),
				object.getName(),
				calDate,
				object.getRating().toString(), 
				object.getBasePrice(),
				aud.getId()
				);
		logger.info("DB dao: event was saved");
		return object;
	}

	@Override
	public void remove(EventEntity object) {
		final String sqlDelete = "DELETE FROM events WHERE ID=?";
		jdbcTemplate.update(sqlDelete, object.getId());
		logger.info("DB dao: event was deleted");

	}

	@Override
	public EventEntity getById(Long id) {
		
		List<EventEntity> result = jdbcTemplate.query(
				"SELECT * FROM events WHERE events.id = ?", new EventRowMapper(auditoriumDAO),
				new Object[] { id });
		
		return result != null ? result.get(0) : null ;
	}

	@Override
	public Collection<EventEntity> getAll() {
		return jdbcTemplate.query("SELECT * FROM events",
				new EventRowMapper(auditoriumDAO));
	}

	@Override
	public EventEntity getByName(String name) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM events WHERE events.name = ?",
				new Object[] { name }, new EventRowMapper(auditoriumDAO));
	}

}
