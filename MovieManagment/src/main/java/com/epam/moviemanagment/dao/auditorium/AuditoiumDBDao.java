package com.epam.moviemanagment.dao.auditorium;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.dao.user.UserRowMapper;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;

@Component("UserDBDAOBean")
@Scope("prototype")
public class AuditoiumDBDao implements AuditoriumDAO {

	private final static String AUDITORIUMS_SELECT_ALL = "SELECT * FROM auditorium";
	private final static String AUDITORIUMS_SELECT_BY_NAME = "SELECT * FROM auditorium WHERE auditorium.name = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Set<AuditoriumEntity> getAll() {
		List<AuditoriumEntity> auditoriums = this.jdbcTemplate.query(
				AUDITORIUMS_SELECT_ALL, new AuditoriumRowMapper());
		return new HashSet<AuditoriumEntity>(auditoriums);
	}

	@Override
	public AuditoriumEntity getByName(String name) {
		return jdbcTemplate.queryForObject(AUDITORIUMS_SELECT_BY_NAME,
				new Object[] { name }, new AuditoriumRowMapper());
	}

	@Override
	public AuditoriumEntity getById(Long id) {
		
		List<AuditoriumEntity> result = jdbcTemplate.query(
				"SELECT * FROM auditorium WHERE auditorium.id = ?", new AuditoriumRowMapper(),
				new Object[] { id });
		
		return result != null ? result.get(0) : null ;
	}

}
