package com.ssafy.pjt.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dao.FollowDao;
import com.ssafy.pjt.model.dao.UserDao;
import com.ssafy.pjt.model.dao.VideoDao;
import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.dto.Video;

@Service
public class UserServiceImpl implements UserService{
	
	UserDao ud;
	VideoDao vd;
	FollowDao fd;
	
	public UserServiceImpl(UserDao ud,VideoDao vd,FollowDao fd) {
		this.ud = ud;
		this.vd = vd;
		this.fd = fd;
	}

	
	@Override
	public User checkUser(String userId, String userPassword) { // 이름 변경
	    Map<String, String> info = new HashMap<>();
	    info.put("userId", userId);
	    info.put("userPassword", userPassword); // 이름 변경
	    User tmp = ud.selectOne(info);
	    return tmp;
	}

	@Override
	public boolean signup(User user) {
		return 1==ud.create(user);
	}


	@Override
	public List<Video> getVideoList(String userId) {
		return vd.getFollowList(userId);
	}


	@Override
	public boolean addFavorite(String userId, int videoId) {
		
		return 1==fd.insertFavorite(userId,videoId);
	}


	@Override
	public boolean deleteFavorite(String userId, int videoId) {
		return 1==fd.deleteFavorite(userId,videoId);
	}
	
}
