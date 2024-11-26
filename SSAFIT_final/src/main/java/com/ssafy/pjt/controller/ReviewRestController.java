package com.ssafy.pjt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/videos/{videoId}") // 비디오 ID에 따라 리뷰 관련 API 제공
@Tag(name = "Review", description = "Review 관련 API") // Swagger용 태그 추가
public class ReviewRestController {

    private final ReviewService reviewService; // 리뷰 서비스

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 특정 비디오의 모든 리뷰를 조회합니다.
     * @param videoId 비디오 ID
     * @return 리뷰 목록 또는 상태 메시지
     */
    @Operation(summary = "특정 운동 영상에 대한 리뷰 목록을 조회합니다.")
    @GetMapping("/reviews")
    public ResponseEntity<?> getReview(@PathVariable("videoId") String videoId) {
        List<Review> reviews = reviewService.selectByVideoId(videoId); // 특정 비디오 ID 기반 조회
        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 영상에 대한 리뷰가 없습니다.");
        }
        return ResponseEntity.ok(reviews);
    }

    /**
     * 특정 운동 영상에 리뷰를 추가합니다.
     * @param videoId 비디오 ID
     * @param review 추가할 리뷰 정보
     * @return 성공 메시지 또는 상태 메시지
     */
    @Operation(summary = "특정 운동 영상에 리뷰를 추가합니다.")
    @PostMapping("/reviews")
    public ResponseEntity<?> postReview(@PathVariable("videoId") String videoId, @RequestBody Review review) {
        if (review == null || review.getReviewTitle() == null || review.getReviewContent() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 제목과 내용을 모두 입력해주세요.");
        }

        // 비디오 ID 설정
        review.setVideoId(videoId);

        try {
            boolean result = reviewService.writeReview(review);
            if (result) {
                return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 성공적으로 추가되었습니다.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 추가 중 오류가 발생했습니다.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 추가에 실패했습니다.");
    }


    /**
     * 특정 리뷰 내용을 수정합니다.
     * @param videoId 비디오 ID
     * @param reviewTitle 수정할 리뷰 제목
     * @param review 수정할 내용이 포함된 리뷰 객체
     * @return 성공 메시지 또는 상태 메시지
     */
    @Operation(summary = "특정 리뷰 내용을 수정합니다.")
    @PutMapping("/reviews/{reviewTitle}")
    public ResponseEntity<?> modifyReview(@PathVariable("videoId") String videoId, 
                                          @PathVariable("reviewTitle") String reviewTitle, 
                                          @RequestBody Review review) {
        review.setVideoId(videoId); // 비디오 ID 설정
        review.setReviewTitle(reviewTitle); // 리뷰 제목 설정
        try {
            boolean result = reviewService.modifyReview(review);
            if (result) {
                return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 수정 중 오류가 발생했습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 수정에 실패했습니다.");
    }

    /**
     * 특정 리뷰를 삭제합니다.
     * @param reviewTitle 삭제할 리뷰 제목
     * @return 성공 메시지 또는 상태 메시지
     */
    @Operation(summary = "특정 리뷰를 삭제합니다.")
    @DeleteMapping("/reviews/{reviewTitle}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewTitle") String reviewTitle) {
        try {
            boolean result = reviewService.deleteReview(reviewTitle); // 리뷰 제목으로 삭제
            if (result) {
                return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 삭제 중 오류가 발생했습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 삭제에 실패했습니다.");
    }
}
