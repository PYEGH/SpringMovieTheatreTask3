package com.epam.moviemanagment.aspect;

import java.util.Calendar;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.dto.User;

@Component("luckyWinnerAspect")
@Aspect
public class LuckyWinnerAspect {

	private final Logger logger = Logger.getLogger(LuckyWinnerAspect.class);

	private int luckyNumber;

	public LuckyWinnerAspect(int luckyNumber) {
		super();
		this.luckyNumber = luckyNumber;
	}

	@Pointcut("execution(* com.epam.moviemanagment.service.BookingService.getTicketsPrice(..))")
	private void getTicketsPrice() {
	}

	@Around("getTicketsPrice() && args(event,dateTime,user,seats)")
	public double countDiscounts(final ProceedingJoinPoint joinPoint,
			final Event event, final Calendar dateTime, final User user,
			final Set<Long> seats) throws Throwable {
		logger.info("Lucky Aspect work is in progress");
		if (isLucky(user)) {

			return 0.0;
		}
		return (double) joinPoint.proceed(joinPoint.getArgs());
	}

	/**
	 * Only user with not empty email could be lucky.
	 * 
	 * @return
	 */
	private boolean isLucky(final User user) {
		Random rand = new Random();
		return rand.nextInt(10) == this.luckyNumber
				&& !StringUtils.isEmpty(user.getEmail());
	}
}
