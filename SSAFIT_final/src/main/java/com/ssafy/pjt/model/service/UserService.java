package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.Video;
import com.ssafy.pjt.model.dto.User;


public interface UserService {

	public User checkUser(String userId, String userPassword);

	boolean signup(User user);
	
	List<Video> getVideoList(String userId);

	boolean addFavorite(String userId, int videoId);

	boolean deleteFavorite(String userId, int videoId);
}
