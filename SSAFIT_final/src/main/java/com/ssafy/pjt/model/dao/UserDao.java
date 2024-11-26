package com.ssafy.pjt.model.dao;

import java.util.Map;

import com.ssafy.pjt.model.dto.User;

public interface UserDao {

	//유저 조회
	public User selectOne(Map<String, String> info);

	int create(User user);
}
