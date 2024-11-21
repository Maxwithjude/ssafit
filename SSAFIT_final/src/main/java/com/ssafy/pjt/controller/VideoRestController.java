package com.ssafy.pjt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.SearchCondition;
import com.ssafy.pjt.model.dto.Video;
import com.ssafy.pjt.model.service.VideoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/videos")
@Tag(name = "Video", description = "Video 관련 API")
public class VideoRestController {
	
	private final VideoService vs;

	public VideoRestController(VideoService vs) {
		this.vs = vs;
	}
	
	//api로 받은 검색 비디오 저장 
	@PostMapping()
    public ResponseEntity<?> saveVideos(@RequestBody List<Video> videos) {
        try {
            vs.saveVideos(videos);
            return ResponseEntity.ok("Videos saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save videos");
        }
    }
	
	@Operation(summary = "비디오 전체 리스트를 조회합니다.")
	@GetMapping()
	public ResponseEntity<List<Video>> videoList() {
		List<Video> videos = vs.selectAll();
		if(videos == null || videos.size() == 0)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(videos);
	}
	
	@Operation(summary = "비디오의 상세정보를 조회합니다.")
	@GetMapping("/{videoId}")
	public ResponseEntity<?> videoDetail(@PathVariable("videoId") int id) {
		Video video = vs.selectOne(id);
		if(video == null) 
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(video);
	}
	
	@Operation(summary = "비디오를 검색합니다.")
	@GetMapping("/search")
	public ResponseEntity<?> searchVideo(@ModelAttribute SearchCondition sc) {
		List<Video> result = vs.searchVideo(sc);
		if(result == null || result.size() <= 0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(result);
	}

}
