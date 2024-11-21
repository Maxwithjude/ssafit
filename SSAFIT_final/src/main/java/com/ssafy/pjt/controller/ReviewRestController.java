package com.ssafy.pjt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.Review;
import com.ssafy.pjt.model.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/videos/{videoId}")
@Tag(name = "Review", description = "Review 관련 API")
public class ReviewRestController {

	private final ReviewService rs;

	public ReviewRestController(ReviewService rs) {
		this.rs = rs;
	}
	//리뷰 검색도 추가해야함 
	@Operation(summary = "특정 운동 영상에 대한 리뷰 목록을 조회합니다.")
	@GetMapping("/reviews")
	public ResponseEntity<?> getReview(@PathVariable("videoId") int id) {
		List<Review> reviews = rs.selectAll();
		if (reviews == null || reviews.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Review T^T");
		return ResponseEntity.ok(reviews);
	}
	
	@Operation(summary = "특정 운동 영상에 리뷰를 추가합니다.")
	@PostMapping("/reviews")
	public ResponseEntity<?> postReview(@PathVariable("videoId") int id, @ModelAttribute Review review) {
		boolean result;
		try {
			result = rs.writeReview(review);
			if (result)
				return ResponseEntity.ok(review);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("T^T");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("T^T");
	}
	
	@Operation(summary = "특정 리뷰 내용을 수정합니다.")
	@PutMapping("/reviews/{reviewTitle}")
	public ResponseEntity<?> modifyReview(@PathVariable("videoId") int vid, @PathVariable("reviewTitle") String title, @RequestBody Review review) {
		boolean result;
		try {
			result = rs.modifyReview(review);
			if(result)
				return ResponseEntity.ok().body("수정이 반영되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("T^T");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("T^T");
	}
	
	@Operation(summary = "특정 리뷰를 삭제합니다.")
	@DeleteMapping("/reviews/{reviewTitle}")
	public ResponseEntity<?> deleteReview(@PathVariable("reviewTitle") String title) {
		boolean result;
		try {
			result = rs.deleteReview(title);
			if(result)
				return ResponseEntity.ok().body("삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("T^T");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("T^T");
	}
}
