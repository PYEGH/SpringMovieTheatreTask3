package com.epam.moviemanagment.service.discount;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.aspect.CounterAspect;
import com.epam.moviemanagment.domain.dto.User;

@Component("birthdayDiscountStrategyBean")
@Scope("prototype")
public class BirthdayDiscountStrategy extends AbstractDiscountStrategy {

	private final Logger logger = Logger
			.getLogger(BirthdayDiscountStrategy.class);

	public BirthdayDiscountStrategy(final Double discount) {
		super.setDiscount(discount);

	}

	@Override
	public double calculateDiscount(User user, Calendar date) {
		final int eventDayOfMonth = date.get(Calendar.DAY_OF_MONTH);
		final int userDayOfMonth = user.getBirthday()
				.get(Calendar.DAY_OF_MONTH);
		final int eventMonth = date.get(Calendar.MONTH);
		final int userMonth = user.getBirthday().get(Calendar.MONTH);

		return eventDayOfMonth == userDayOfMonth && eventMonth == userMonth ? this.discount
				: NO_DISCOUNT;
	}

	@Override
	public double getDiscount() {
		return this.discount;
	}

	@Override
	public void setDiscount(final double discount) {
		logger.info(discount);
		this.discount = discount;
	}

}
