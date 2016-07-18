package com.epam.moviemanagment.service;

import com.epam.moviemanagment.domain.dto.User;



public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    User getUserByEmail(String email);   


}
