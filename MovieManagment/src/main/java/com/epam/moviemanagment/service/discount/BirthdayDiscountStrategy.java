package com.epam.moviemanagment.service.discount;

import java.util.Calendar;


import com.epam.moviemanagment.domain.dto.User;

public class BirthdayDiscountStrategy extends AbstractDiscountStrategy {


	public BirthdayDiscountStrategy(final Double discount) {
		super.setDiscount(discount);

	}

	@Override
	public double calculateDiscount(User user, Calendar date) {
		final int eventDayOfMonth = date.get(Calendar.DAY_OF_MONTH);
		final int userDayOfMonth = user.getBirthday().get(Calendar.DAY_OF_MONTH);

		return eventDayOfMonth == userDayOfMonth ? this.discount : NO_DISCOUNT;
	}
}
