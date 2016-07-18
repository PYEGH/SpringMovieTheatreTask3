package com.epam.moviemanagment.service.discount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.dto.User;

/**
 * Class is responsible for counting of a discount to users with certain name.
 * List of names for which discount are available are injected via spring.
 */
@Component("nameDiscountStrategyBean")
@Scope("prototype")
public class NameDiscountStrategy extends AbstractDiscountStrategy {
	private List<String> namesForDiscount = new ArrayList<>();


	public NameDiscountStrategy(final Double discount, final List<String> namesForDiscount) {
		super.setDiscount(discount);
		this.namesForDiscount = namesForDiscount;
	}

	@Override
	public double calculateDiscount(final User user, final Calendar date) {
		final String userFirstName = user.getFirstName();
		return userFirstName != null
				&& namesForDiscount.contains(userFirstName) ? this.discount
				: NO_DISCOUNT;
	}

	public List<String> getNamesForDiscount() {
		return this.namesForDiscount;
	}

	public void setNamesForDiscount(final List<String> namesForDiscount) {
		this.namesForDiscount = namesForDiscount;
	}
	
	@Override
	public double getDiscount() {
		return this.discount;
	}

	public void setDiscount(final Double discount) {
		this.discount = discount;
	}

}
