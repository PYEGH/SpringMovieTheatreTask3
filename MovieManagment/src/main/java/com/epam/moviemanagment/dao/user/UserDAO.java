package com.epam.moviemanagment.dao.user;


import com.epam.moviemanagment.dao.AbstractDomainObjectDAO;
import com.epam.moviemanagment.domain.entity.UserEntity;

public interface UserDAO extends AbstractDomainObjectDAO<UserEntity> {
    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    UserEntity getUserByEmail(String email);
}
