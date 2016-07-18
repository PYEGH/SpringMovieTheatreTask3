package com.epam.moviemanagment.demonstration;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.moviemanagment.configuration.MovieManagmentConfiguration;


public class Runner {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				MovieManagmentConfiguration.class);
		Demonstrator demonstrator = (Demonstrator) ctx.getBean("demonstratorBean");
		
		demonstrator.demonstrateAuditoriumsViewing();
		demonstrator.demonstrateUserCreation();
		demonstrator.demonstrateTicketsBooking();
		demonstrator.demonstrateEventGettingAndPriceCalculating();
		
		ctx.close();
	}

}
