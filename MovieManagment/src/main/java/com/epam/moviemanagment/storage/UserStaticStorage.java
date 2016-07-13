package com.epam.moviemanagment.storage;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeSet;

import com.epam.moviemanagment.domain.entity.TicketEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;

;

/**
 * Class is simulate storage for Users.
 * 
 */
public class UserStaticStorage {
	private static List<UserEntity> users = new ArrayList<>();

	static {
		final UserEntity user1 = new UserEntity();
		user1.setAdmin(true);
		user1.setEmail("user1@epam.com");
		user1.setFirstName("Fn1");
		user1.setLastName("Ln1");
		user1.setId((long) 1);
		user1.setTickets(new TreeSet<TicketEntity>());
		user1.setBirthday(new GregorianCalendar(1980, 7, 10, 15, 0));

		final UserEntity user2 = new UserEntity();
		user2.setAdmin(true);
		user2.setEmail("user2@epam.com");
		user2.setFirstName("Fn2");
		user2.setLastName("Ln2");
		user2.setId((long) 2);
		user2.setTickets(new TreeSet<TicketEntity>());
		user2.setBirthday(new GregorianCalendar(1980, 7, 15, 15, 0));

		users.add(user1);
		users.add(user2);
	}

	public static List<UserEntity> getUsers() {
		return users;
	}

	public static void setUsers(List<UserEntity> users) {
		UserStaticStorage.users = users;
	}

}
