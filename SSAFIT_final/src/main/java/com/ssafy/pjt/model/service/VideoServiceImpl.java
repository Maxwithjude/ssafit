package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dao.VideoDao;
import com.ssafy.pjt.model.dto.SearchCondition;
import com.ssafy.pjt.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {
	
	private final VideoDao vd;

	public VideoServiceImpl(VideoDao vd) {
		this.vd = vd;
	}

	@Override
	public List<Video> selectAll() {
		return vd.selectAll();
	}

	@Override
	public Video selectOne(int id) {
		return vd.selectOne(id);
	}
	//검색
	@Override
	public List<Video> searchVideo(SearchCondition sc) {
		return vd.search(sc);
	}
	
	@Override
	public void saveVideos(List<Video> videos) {
		vd.insertVideos(videos);
		}

}
