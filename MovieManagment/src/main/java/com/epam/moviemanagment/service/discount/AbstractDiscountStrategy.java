package com.epam.moviemanagment.service.discount;

import java.util.Calendar;
import java.util.Date;

import com.epam.moviemanagment.domain.dto.User;

public abstract class AbstractDiscountStrategy implements DiscountStrategy {
	public static final double NO_DISCOUNT = 0;
	protected double discount;

	public abstract double calculateDiscount(User user, Calendar date);

	public double getDiscount() {
		return this.discount;
	}

	public void setDiscount(final double discount) {
		this.discount = discount;
	}
}
