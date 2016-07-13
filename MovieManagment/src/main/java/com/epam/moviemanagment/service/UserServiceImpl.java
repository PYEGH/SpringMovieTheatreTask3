package com.epam.moviemanagment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.epam.moviemanagment.dao.user.UserDAO;
import com.epam.moviemanagment.domain.converter.UserConverter;
import com.epam.moviemanagment.domain.dto.User;
import com.epam.moviemanagment.domain.entity.UserEntity;

public class UserServiceImpl implements UserService {

	private UserDAO userDao;

	public UserServiceImpl(final UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public User save(final User userDto) {
		final UserEntity userEntity = UserConverter.toEntity(userDto);
		this.userDao.save(userEntity);
		return userDto;
	}

	@Override
	public void remove(final User userDto) {
		final UserEntity userEntity = UserConverter.toEntity(userDto);
		this.userDao.remove(userEntity);
	}

	@Override
	public User getById(final Long id) {

		final UserEntity userEntity = this.userDao.getById(id);
		if (userEntity != null) {
			return UserConverter.toDto(userEntity);
		}
		return null;
	}

	@Override
	public Collection<User> getAll() {
		final List<User> users = new ArrayList<>();
		final List<UserEntity> userEntities = (List<UserEntity>) this.userDao
				.getAll();
		for (final UserEntity userEntity : userEntities) {
			final User user = UserConverter.toDto(userEntity);
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserByEmail(String email) {
		final UserEntity userEntity = this.userDao.getUserByEmail(email);
		if (userEntity != null) {
			return UserConverter.toDto(userEntity);
		}
		return null;
	}

}
