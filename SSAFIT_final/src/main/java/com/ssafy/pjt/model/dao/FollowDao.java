package com.ssafy.pjt.model.dao;

public interface FollowDao {

	int insertFavorite(String userId, int videoId);

	int deleteFavorite(String userId, int videoId);
}
