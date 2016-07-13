package com.epam.moviemanagment.service.discount;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.User;

public class DiscountServiceImpl implements DiscountService {

	private List<DiscountStrategy> discountStrategies;

	public DiscountServiceImpl(List<DiscountStrategy> discountStrategies) {
		this.discountStrategies = discountStrategies;
	}

	@Override
	public double getDiscount(final User user, final Event event,
			final Calendar airDateTime, final long numberOfTickets) {
		double maxDiscount = 0;
		for (final DiscountStrategy strategy : this.discountStrategies) {
			final double discount = strategy.calculateDiscount(user,
					airDateTime);
			if (discount > maxDiscount) {
				maxDiscount = discount;
			}
		}
		return maxDiscount;
	}

	public List<DiscountStrategy> getDiscountStrategies() {
		return discountStrategies;
	}

	public void setDiscountStrategies(List<DiscountStrategy> discountStrategies) {
		this.discountStrategies = discountStrategies;
	}

}
