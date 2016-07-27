package com.epam.moviemanagment.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.moviemanagment.domain.entity.UserEntity;

public class UserRowMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    	final int id = rs.getInt("ID");
    	final String firstName = rs.getString("FIRST_NAME");
    	final String lastName = rs.getString("LAST_NAME");
    	final String email = rs.getString("EMAIL");
    	final String birthDay = rs.getString("BIRTHDAY");
    	final int admin = rs.getInt("ADMIN");
    	
    	final UserEntity userEntity = new UserEntity();
    	userEntity.setAdmin(admin == 1);
    	//userEntity.setBirthday(birthday);
    	userEntity.setEmail(email);
    	userEntity.setId(new Long(id));
    	userEntity.setFirstName(firstName);
    	userEntity.setLastName(lastName);
    	
        return userEntity;
    }


}