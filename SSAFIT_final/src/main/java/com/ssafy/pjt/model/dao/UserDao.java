package com.ssafy.pjt.model.dao;

import com.ssafy.pjt.model.dto.User;

public interface UserDao {

	User checkUser(User user);

	int create(User user);
}
