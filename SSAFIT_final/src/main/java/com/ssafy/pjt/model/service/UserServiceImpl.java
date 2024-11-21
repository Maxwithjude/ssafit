package com.ssafy.pjt.model.service;

import java.util.List;

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
	public User checkUser(User user) {
		return ud.checkUser(user);
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
