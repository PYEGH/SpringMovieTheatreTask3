package com.epam.moviemanagment.service.discount;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.User;

@Component("discountServiceBean")
@Scope("prototype")
public class DiscountServiceImpl implements DiscountService {
	@Autowired
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
