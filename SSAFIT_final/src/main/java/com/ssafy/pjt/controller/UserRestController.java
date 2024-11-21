package com.ssafy.pjt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.Video;
import com.ssafy.pjt.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@RestController
@RequestMapping("/api/users/{userId}")
@Tag(name = "User", description = "User 관련 API")
public class UserRestController {
	
	UserService us;
	
	
	public UserRestController(UserService us) {
		this.us = us;
	}
	
	@Operation(summary = "특정 사용자가 찜한 영상 목록을 조회합니다.")
	@GetMapping("/favorites")
	public ResponseEntity<?> getList(@PathVariable("userId")String userId){
		List<Video> videoList;
		try {
			videoList = us.getVideoList(userId);
			if(videoList.size()==0) {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("그런건 없어용");
		}
		return ResponseEntity.ok(videoList);
	}
	
	@Operation(summary = "사용자가 특정 영상을 찜 목록에 추가합니다.")
	@PostMapping("/favorites")
	public ResponseEntity<?> addFavorite(@PathVariable("userId")String userId,@RequestBody int videoId){
		boolean result;
		try {
			result = us.addFavorite(userId,videoId);
			if(result) {
				return ResponseEntity.ok().body("성공했어용");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("실패했어용");
		}
		return ResponseEntity.badRequest().body("실패했어용");
	}
	
	
	@Operation(summary = "사용자가 특정 영상 찜을 삭제합니다.")
	@DeleteMapping("/favorites/{videoId}")
	public ResponseEntity<?> deleteFavorite(@PathVariable("userId")String userId,@PathVariable("videoId") int videoId){
		boolean result;
		try {
			result = us.deleteFavorite(userId,videoId);
			if(result) {
				return ResponseEntity.ok().body("삭제했어용");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("삭제못했어용");
		}
		return ResponseEntity.badRequest().body("삭제못했어용");
	}
}
