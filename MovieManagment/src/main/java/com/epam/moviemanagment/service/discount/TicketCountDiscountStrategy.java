package com.epam.moviemanagment.service.discount;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.dto.User;

/**
 * Class is responsible for counting of a discount to users with certain number
 * of bought tickets. Number of bought tickets enough for discount is injected
 * via spring.
 */
@Component("ticketCountDiscountStrategyBean")
@Scope("prototype")
public class TicketCountDiscountStrategy extends AbstractDiscountStrategy {

	private int countOfTicketForDiscount;

	public TicketCountDiscountStrategy(final int countOfTicketForDiscount,
			final Double discount) {
		this.countOfTicketForDiscount = countOfTicketForDiscount;
		super.setDiscount(discount);
	}

	@Override
	public double calculateDiscount(final User user, final Calendar date) {
		int userBookedTickedTotalCount = 0;
		if (user != null && user.getTickets() != null) {
			userBookedTickedTotalCount = user.getTickets().size();
		}
		return userBookedTickedTotalCount > countOfTicketForDiscount ? this.discount
				: NO_DISCOUNT;
	}

	public int getCountOfTicketForDiscount() {
		return this.countOfTicketForDiscount;
	}

	public void setCountOfTicketForDiscount(final int countOfTicketForDiscount) {
		this.countOfTicketForDiscount = countOfTicketForDiscount;
	}

	@Override
	public double getDiscount() {
		return this.discount;
	}

	@Override
	public void setDiscount(final double discount) {
		this.discount = discount;
	}

}
