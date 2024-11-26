package com.ssafy.pjt.model.service;

import java.util.List;

import com.ssafy.pjt.model.dto.Review;

public interface ReviewService {

    /**
     * 특정 비디오의 모든 리뷰 조회
     * @param videoId 비디오 ID
     * @return 리뷰 목록
     */
    List<Review> selectByVideoId(String videoId);

    /**
     * 리뷰 작성
     * @param review 작성할 리뷰 객체
     * @return 작성 성공 여부
     */
    boolean writeReview(Review review);

    /**
     * 리뷰 수정
     * @param review 수정할 리뷰 객체
     * @return 수정 성공 여부
     */
    boolean modifyReview(Review review);

    /**
     * 특정 리뷰 삭제
     * @param reviewTitle 삭제할 리뷰 제목
     * @return 삭제 성공 여부
     */
    boolean deleteReview(String reviewTitle);
}
