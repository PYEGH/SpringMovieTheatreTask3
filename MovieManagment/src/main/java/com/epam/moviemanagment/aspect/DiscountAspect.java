package com.epam.moviemanagment.aspect;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.service.discount.BirthdayDiscountStrategy;
import com.epam.moviemanagment.service.discount.DiscountType;
import com.epam.moviemanagment.service.discount.NameDiscountStrategy;
import com.epam.moviemanagment.service.discount.TicketCountDiscountStrategy;

/**
 * Counts how many times each discount was given total and for specific user.
 * 
 */

@Component("discountAspect")
@Aspect
public class DiscountAspect {

	private final Logger logger = Logger.getLogger(DiscountAspect.class);

	private Map<User, Integer> ticketDiscountUsage = new HashMap<>();
	private Map<User, Integer> nameDiscountUsage = new HashMap<>();
	private Map<User, Integer> birthdayDiscountUsage = new HashMap<>();

	private Map<DiscountType, Integer> totalCountMap = new HashMap<>();

	@Pointcut("execution(* com.epam.moviemanagment.service.discount.NameDiscountStrategy.calculateDiscount(..))")
	public void getDiscountForNameDiscountStrategy() {
	}

	@Pointcut("execution(* com.epam.moviemanagment.service.discount.TicketCountDiscountStrategy.calculateDiscount(..))")
	public void getDiscountForTicketCountDiscountStrategy() {
	}

	@Pointcut("execution(* com.epam.moviemanagment.service.discount.BirthdayDiscountStrategy.calculateDiscount(..))")
	public void calculateDiscountForBirthdayDiscountStrategy() {
	}

	@After("calculateDiscountForBirthdayDiscountStrategy() && args(user,date)")
	public void countBirthdayDiscountUsage(final JoinPoint joinPoint,
			final User user, final Calendar date) {
		countDiscountUsage(joinPoint, user, date);
	}

	@AfterReturning("getDiscountForNameDiscountStrategy() && args(user,date)")
	public void countNameDiscountUsage(final JoinPoint joinPoint,
			final User user, final Calendar date) {
		countDiscountUsage(joinPoint, user, date);
	}

	@AfterReturning("getDiscountForTicketCountDiscountStrategy() && args(user,date)")
	public void countTicketDiscountUsage(final JoinPoint joinPoint,
			final User user, final Calendar date) {
		countDiscountUsage(joinPoint, user, date);
	}

	private void countDiscountUsage(final JoinPoint joinPoint, final User user,
			final Calendar date) {
		// Counting for certain user
		Map<User, Integer> resultMap = defineMap(joinPoint);
		if (!resultMap.containsKey(user)) {
			resultMap.put(user, 0);
		}
		resultMap.put(user, resultMap.get(user) + 1);

		// Counting of total count for each type
		final DiscountType type = defineDiscountType(joinPoint);
		if (!this.totalCountMap.containsKey(type)) {
			this.totalCountMap.put(type, 0);
		}
		this.totalCountMap.put(type, this.totalCountMap.get(type) + 1);

	}

	private Map<User, Integer> defineMap(final JoinPoint joinPoint) {
		final Class clazz = joinPoint.getTarget().getClass();
		Map<User, Integer> resultMap = null;
		if (clazz.equals(TicketCountDiscountStrategy.class)) {
			resultMap = this.ticketDiscountUsage;
		} else if (clazz.equals(NameDiscountStrategy.class)) {
			resultMap = this.nameDiscountUsage;
		} else if (clazz.equals(BirthdayDiscountStrategy.class)) {
			resultMap = this.birthdayDiscountUsage;
		}
		return resultMap;
	}

	private DiscountType defineDiscountType(final JoinPoint joinPoint) {
		final Class clazz = joinPoint.getTarget().getClass();
		if (clazz.equals(TicketCountDiscountStrategy.class)) {
			return DiscountType.TICKET;
		} else if (clazz.equals(NameDiscountStrategy.class)) {
			return DiscountType.NAME;
		} else if (clazz.equals(BirthdayDiscountStrategy.class)) {
			return DiscountType.BIRTHDAY;
		}
		return null;
	}
}
