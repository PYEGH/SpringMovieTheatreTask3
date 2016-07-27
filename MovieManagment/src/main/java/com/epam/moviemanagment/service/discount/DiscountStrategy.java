package com.epam.moviemanagment.service.discount;

import java.util.Calendar;
import java.util.Date;

import com.epam.moviemanagment.domain.dto.User;

public interface DiscountStrategy {
	double calculateDiscount(User user, Calendar date);
}
