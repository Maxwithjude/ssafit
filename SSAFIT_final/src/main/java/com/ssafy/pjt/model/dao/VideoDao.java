package com.ssafy.pjt.model.dao;

import java.util.List;

import com.ssafy.pjt.model.dto.SearchCondition;
import com.ssafy.pjt.model.dto.Video;

public interface VideoDao {

	List<Video> selectAll();

	Video selectOne(int id);

	List<Video> search(SearchCondition sc);
	
	List<Video> getFollowList(String userId);
	//비디오 저장
	void insertVideos(List<Video> videos);
	//이미 저장된 비디오인지 확인
//	boolean existsByVideoId(String videoId);
//	비효율적인 로직이라 생각해서 빼고 한번에 insertvideos mapper에서 처리
}
