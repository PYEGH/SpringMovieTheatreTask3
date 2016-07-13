package com.epam.moviemanagment.demonstration;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Runner {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml");
		Demonstrator demonstrator = (Demonstrator) ctx.getBean("demonstrator");
		
		demonstrator.demonstrateAuditoriumsViewing();
		demonstrator.demonstrateUserCreation();
		demonstrator.demonstrateTicketsBooking();
		demonstrator.demonstrateEventGettingAndPriceCalculating();
		
		ctx.close();
	}

}
