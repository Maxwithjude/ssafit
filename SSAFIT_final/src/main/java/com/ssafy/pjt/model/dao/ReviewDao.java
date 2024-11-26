package com.ssafy.pjt.model.dao;

import java.util.List;

import com.ssafy.pjt.model.dto.Review;

public interface ReviewDao {

    /**
     * 특정 비디오의 리뷰 조회
     * @param videoId 비디오 ID
     * @return 해당 비디오에 대한 리뷰 목록
     */
    List<Review> selectByVideoId(String videoId);

    /**
     * 리뷰 작성
     * @param review 작성할 리뷰
     * @return 성공 여부 (1: 성공, 0: 실패)
     */
    int writeReview(Review review);

    /**
     * 리뷰 수정
     * @param review 수정할 리뷰
     * @return 성공 여부 (1: 성공, 0: 실패)
     */
    int modifyReview(Review review);

    /**
     * 특정 리뷰 삭제
     * @param reviewTitle 삭제할 리뷰 제목
     * @param videoId 비디오 ID
     * @return 성공 여부 (1: 성공, 0: 실패)
     */
    int deleteReview(String reviewTitle, String videoId);
}
