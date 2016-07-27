package com.epam.moviemanagment.dao.auditorium;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import com.epam.moviemanagment.domain.converter.AuditoriumConverter;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;

public class AuditoriumRowMapper implements RowMapper<AuditoriumEntity> {

	@Override
	public AuditoriumEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		final int id = rs.getInt("ID");
		final int capacity = rs.getInt("CAPACITY");
		final String name = rs.getString("NAME");
		final String vipSeatsString = rs.getString("VIP_SEATS");

		final Set<Long> vipSeats = AuditoriumConverter
				.convertStringWithVipSeatsToLongList(vipSeatsString);

		final AuditoriumEntity auditoriumEntity = new AuditoriumEntity();
		auditoriumEntity.setId(id);
		auditoriumEntity.setName(name);
		auditoriumEntity.setNumberOfSeats(capacity);
		auditoriumEntity.setVipSeats(vipSeats);

		return auditoriumEntity;
	}

}
