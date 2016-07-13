package com.epam.moviemanagment.dao.user;

import java.util.Collection;
import java.util.List;

import com.epam.moviemanagment.domain.entity.DomainObjectEntity;
import com.epam.moviemanagment.domain.entity.UserEntity;
import com.epam.moviemanagment.storage.UserStaticStorage;

public class UserStaticDAO implements UserDAO {

	/**
	 * Method saves user. If user already exists, his data will be updated.
	 */
	@Override
	public UserEntity save(final UserEntity object) {
		final UserEntity foundUser = getById(object.getId());
		if (foundUser != null) {
			remove(foundUser);
		}
		UserStaticStorage.getUsers().add(object);
		return object;
	}

	@Override
	public void remove(final UserEntity user) {
		UserStaticStorage.getUsers().remove(user);
	}

	@Override
	public UserEntity getById(final Long id) {
		final List<UserEntity> users = UserStaticStorage.getUsers();
		for (final UserEntity user : users) {
			if (id.equals(user.getId())) {
				return user;
			}
		}
		return null;
	}

	@Override
	public Collection<UserEntity> getAll() {
		return UserStaticStorage.getUsers();
	}

	@Override
	public UserEntity getUserByEmail(final String email) {
		final List<UserEntity> users = UserStaticStorage.getUsers();
		for (final UserEntity user : users) {
			if (email != null && email.equals(user.getEmail())) {
				return user;
			}
		}
		return null;
	}
}
