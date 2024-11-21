package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.SearchCondition;
import com.ssafy.pjt.model.dto.Video;


public interface VideoService {
	
	List<Video> selectAll();

	Video selectOne(int id);

	List<Video> searchVideo(SearchCondition sc);
	
	void saveVideos(List <Video> videos);
}
