package com.epam.moviemanagment.domain.entity;

import java.util.Calendar;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

public class UserEntity extends DomainObjectEntity {

	private String firstName;

	private String lastName;

	private String email;

	private Calendar birthday;

	private boolean admin;

	private NavigableSet<TicketEntity> ticketEntities = new TreeSet<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public NavigableSet<TicketEntity> getTickets() {
		return ticketEntities;
	}

	public void setTickets(NavigableSet<TicketEntity> ticketEntities) {
		this.ticketEntities = ticketEntities;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserEntity other = (UserEntity) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		return true;
	}

	public Calendar getBirthday() {
		return this.birthday;
	}

	public void setBirthday(final Calendar birthday) {
		this.birthday = birthday;
	}

}
