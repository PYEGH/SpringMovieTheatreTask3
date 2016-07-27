package com.epam.moviemanagment.dao.counter;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.entity.Counter;

@Component("CounterDAOBean")
@Scope("prototype")
public class CounterDao {
	private final Logger logger = Logger.getLogger(CounterDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public final static int DEFAULT_COUNTER_VALUE = 1;

	public int getCounterValue(String type, String additionalInfo) {
		List<Counter> result = jdbcTemplate.query(
				"SELECT * FROM counter where TYPE = ? AND ADDITIONAL_INFO = ?",
				new CounterRowMapper(), new Object[] { type, additionalInfo });

		return result != null && result.size() > 0 ? result.get(0).getValue()
				: 0;
	}

	public void increaseCounterValue(String type, String additionalInfo) {

		String sql = null;
		Integer counterValue = getCounterValue(type, additionalInfo);
		// this check means that counter do not exist(minimal value of existing
		// counter is one)
		if (counterValue <= 0) {
			sql = "INSERT INTO COUNTER (TYPE,ADDITIONAL_INFO,VALUE) values(?,?,?)";
			jdbcTemplate.update(sql, type, additionalInfo,
					DEFAULT_COUNTER_VALUE);
			logger.info("Inserted: " + " " + type + " " + additionalInfo + " "
					+ counterValue);
		} else {
			counterValue++;
			sql = "UPDATE counter set VALUE = ? where TYPE = ? AND ADDITIONAL_INFO = ?";
			jdbcTemplate.update(sql, counterValue, type, additionalInfo);
			System.out.println("Updated: " + " " + type + " " + additionalInfo
					+ " " + counterValue);
		}

	}
}
