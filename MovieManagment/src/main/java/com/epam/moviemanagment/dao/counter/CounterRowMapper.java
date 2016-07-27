package com.epam.moviemanagment.dao.counter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.moviemanagment.domain.entity.Counter;

public class CounterRowMapper implements RowMapper<Counter>{

	@Override
	public Counter mapRow(ResultSet rs, int rownum) throws SQLException {
		final int id = rs.getInt("ID");
		final String type = rs.getString("TYPE");
		final String additionalInfo = rs.getString("ADDITIONAL_INFO");
		final int value = rs.getInt("VALUE"); 
		
		return new Counter(id, type, additionalInfo, value);
	}

}
