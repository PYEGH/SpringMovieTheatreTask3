package com.epam.moviemanagment.dao.user;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.demonstration.Demonstrator;
import com.epam.moviemanagment.domain.entity.UserEntity;

@Component("AuditoiumDBDAOBean")
@Scope("prototype")
public class UserDBDao implements UserDAO {
	
	private final Logger logger = Logger.getLogger(UserDBDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserEntity save(UserEntity object) {

		final String sqlInsert = "INSERT INTO users (ID,FIRST_NAME,LAST_NAME,EMAIL,BIRTHDAY,ADMIN) values (?,?,?,?,?,?)";
		jdbcTemplate.update(sqlInsert, object.getId(), object.getFirstName(),
				object.getLastName(), object.getEmail(), object.getBirthday(),
				object.isAdmin());
		System.out.println("DB dao: user was saved");
		return object;
	}

	@Override
	public void remove(UserEntity object) {
		final String sqlDelete = "DELETE FROM users WHERE ID=?";
		jdbcTemplate.update(sqlDelete, object.getId());
		logger.info("DB dao: user was deleted");
	}

	@Override
	public UserEntity getById(Long id) {			
		List<UserEntity> result = jdbcTemplate.query(
				"SELECT * FROM users WHERE users.id = ?", new UserRowMapper(),
				new Object[] { id });
		
		return result != null ? result.get(0) : null ;
	}

	@Override
	public Collection<UserEntity> getAll() {
		return jdbcTemplate.query("SELECT * FROM users",
				new UserRowMapper());
	}

	/**
	 * This method will throw exception if few users with the same email exist.
	 * So this method was created just at introductory goals. 
	 */
	@Override
	public UserEntity getUserByEmail(String email) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM users WHERE users.email = ?",
				new Object[] { email }, new UserRowMapper());
	}

}
