package com.epam.moviemanagment.service.discount;

import java.util.Calendar;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.User;

public interface DiscountService {

	/**
	 * Getting discount based on some rules for user that buys some number of
	 * tickets for the specific date time of the event
	 * 
	 * @param user
	 *            User that buys tickets. Can be <code>null</code>
	 * @param event
	 *            Event that tickets are bought for
	 * @param airDateTime
	 *            The date and time event will be aired
	 * @param numberOfTickets
	 *            Number of tickets that user buys
	 * @return discount value from 0 to 100
	 */
	double getDiscount(User user, Event event, Calendar airDateTime,
			long numberOfTickets);

}
