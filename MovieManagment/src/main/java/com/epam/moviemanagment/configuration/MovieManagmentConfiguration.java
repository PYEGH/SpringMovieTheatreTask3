package com.epam.moviemanagment.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.epam.moviemanagment.aspect.CounterAspect;
import com.epam.moviemanagment.aspect.DiscountAspect;
import com.epam.moviemanagment.aspect.LuckyWinnerAspect;
import com.epam.moviemanagment.dao.auditorium.AuditoiumDBDao;
import com.epam.moviemanagment.dao.auditorium.AuditoriumDAO;
import com.epam.moviemanagment.dao.auditorium.AuditoriumStaticDAO;
import com.epam.moviemanagment.dao.counter.CounterDao;
import com.epam.moviemanagment.dao.event.EventDAO;
import com.epam.moviemanagment.dao.event.EventDBDao;
import com.epam.moviemanagment.dao.event.EventRowMapper;
import com.epam.moviemanagment.dao.event.EventStaticDAO;
import com.epam.moviemanagment.dao.ticket.TicketDAO;
import com.epam.moviemanagment.dao.ticket.TicketDBDao;
import com.epam.moviemanagment.dao.ticket.TicketStaticDAO;
import com.epam.moviemanagment.dao.user.UserDAO;
import com.epam.moviemanagment.dao.user.UserDBDao;
import com.epam.moviemanagment.dao.user.UserStaticDAO;
import com.epam.moviemanagment.demonstration.Demonstrator;
import com.epam.moviemanagment.service.AuditoriumService;
import com.epam.moviemanagment.service.AuditoriumServiceImpl;
import com.epam.moviemanagment.service.BookingService;
import com.epam.moviemanagment.service.BookingServiceImpl;
import com.epam.moviemanagment.service.EventService;
import com.epam.moviemanagment.service.EventServiceImpl;
import com.epam.moviemanagment.service.UserService;
import com.epam.moviemanagment.service.UserServiceImpl;
import com.epam.moviemanagment.service.discount.BirthdayDiscountStrategy;
import com.epam.moviemanagment.service.discount.DiscountService;
import com.epam.moviemanagment.service.discount.DiscountServiceImpl;
import com.epam.moviemanagment.service.discount.DiscountStrategy;
import com.epam.moviemanagment.service.discount.NameDiscountStrategy;
import com.epam.moviemanagment.service.discount.TicketCountDiscountStrategy;

/**
 * Spring configuration.
 * 
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
@EnableAspectJAutoProxy
public class MovieManagmentConfiguration {

	@Value("${birthday.discount}")
	private Double birthdayDiscount;

	@Value("${name.discount}")
	private Double nameDiscount;

	@Value("${ticket.discount}")
	private Double ticketDiscount;

	@Value("${countTicket.discount}")
	private int countTicketDiscount;

	@Value("#{'${nameList.discount}'.split(',')}")
	private List<String> nameList;

	@Value("${lucky.number}")
	private int luckyNumber;
	
	// DB properties
	@Value("${db.classDriver}")
	private String dbClassDriver;
	
	@Value("${db.url}")
	private String dbUrl;
	
	@Value("${db.user}")
	private String dbUser;
	
	@Value("${db.password}")
	private String dbPassword;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(driverManagerDataSource());
	}

	@Bean(name = "dataSource")
	public DriverManagerDataSource driverManagerDataSource() {
		return new DriverManagerDataSource(dbClassDriver, dbUrl, dbUser, dbPassword);
	}

	@Bean(name = "demonstratorBean")
	public Demonstrator demonstrator() {
		return new Demonstrator(userService(), auditoriumService(),
				bookingService(), discountService(), eventService(),
				ticketDAO());
	}

	@Bean(name = "counterDAOBean")
	public CounterDao counterDao() {
		return new CounterDao();
	}

	@Bean(name = "userDAOBean")
	public UserDAO userDAO() {
		// return new UserStaticDAO();
		return new UserDBDao();
	}

	@Bean(name = "userServiceBean")
	public UserService userService() {
		return new UserServiceImpl(userDAO());
	}

	@Bean(name = "auditoriumDAOBean")
	public AuditoriumDAO auditoriumDAO() {
		return new AuditoiumDBDao();
		// return new AuditoriumStaticDAO();
	}

	@Bean(name = "auditoriumServiceBean")
	public AuditoriumService auditoriumService() {
		return new AuditoriumServiceImpl(auditoriumDAO());
	}

	@Bean(name = "eventDAOBean")
	public EventDAO eventDAO() {
		return new EventDBDao();
		// return new EventStaticDAO();
	}

	@Bean(name = "eventServiceBean")
	public EventService eventService() {
		return new EventServiceImpl(eventDAO());
	}

	@Bean(name = "discountServiceBean")
	public DiscountService discountService() {
		return new DiscountServiceImpl(discountStrategies());
	}

	@Bean(name = "bookingServiceBean")
	public BookingService bookingService() {
		return new BookingServiceImpl(ticketDAO(), userService(),
				discountService());
	}

	@Bean(name = "ticketDAOBean")
	public TicketDAO ticketDAO() {
		return new TicketDBDao();
		// return new TicketStaticDAO();
	}

	@Bean(name = "nameDiscountStrategyBean")
	public DiscountStrategy nameDiscountStrategy() {
		return new NameDiscountStrategy(nameDiscount, nameList);
	}

	@Bean(name = "ticketCountDiscountStrategyBean")
	public DiscountStrategy ticketCountDiscountStrategy() {
		return new TicketCountDiscountStrategy(countTicketDiscount,
				ticketDiscount);
	}

	@Bean(name = "birthdayDiscountStrategyBean")
	public DiscountStrategy birthdayDiscountStrategy() {
		return new BirthdayDiscountStrategy(birthdayDiscount);
	}

	@Bean
	public List<DiscountStrategy> discountStrategies() {
		final List<DiscountStrategy> strategies = new ArrayList<>();
		strategies.add(nameDiscountStrategy());
		strategies.add(ticketCountDiscountStrategy());
		strategies.add(birthdayDiscountStrategy());
		return strategies;
	}

	@Bean(name = "counterAspect")
	public CounterAspect counterAspect() {
		return new CounterAspect(counterDao());
	}

	@Bean(name = "discountAspect")
	public DiscountAspect discountAspect() {
		return new DiscountAspect(counterDao());
	}

	@Bean(name = "luckyWinnerAspect")
	public LuckyWinnerAspect luckyWinnerAspect() {
		return new LuckyWinnerAspect(luckyNumber);
	}
}