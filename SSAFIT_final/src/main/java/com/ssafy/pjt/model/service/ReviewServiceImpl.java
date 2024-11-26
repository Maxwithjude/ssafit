package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dao.ReviewDao;
import com.ssafy.pjt.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    /**
     * 특정 비디오의 모든 리뷰 조회
     * @param videoId 비디오 ID
     * @return 리뷰 목록
     */
    @Override
    public List<Review> selectByVideoId(String videoId) {
        return reviewDao.selectByVideoId(videoId);
    }

    /**
     * 리뷰 작성
     * @param review 작성할 리뷰
     * @return 성공 여부
     */
    @Override
    public boolean writeReview(Review review) {
        // 중복 체크 (예: 같은 videoId와 reviewTitle이 이미 있는지 확인)
        List<Review> existingReviews = reviewDao.selectByVideoId(review.getVideoId());
        boolean isDuplicate = existingReviews.stream()
            .anyMatch(r -> r.getReviewTitle().equals(review.getReviewTitle()));

        if (isDuplicate) {
            throw new IllegalArgumentException("동일한 제목의 리뷰가 이미 존재합니다.");
        }

        return reviewDao.writeReview(review) == 1; // 성공 시 true 반환
    }

    /**
     * 리뷰 수정
     * @param review 수정할 리뷰
     * @return 성공 여부
     */
    @Override
    public boolean modifyReview(Review review) {
        return reviewDao.modifyReview(review) == 1;
    }

    /**
     * 특정 리뷰 삭제
     * @param reviewTitle 삭제할 리뷰 제목
     * @return 성공 여부
     */
    @Override
    public boolean deleteReview(String reviewTitle) {
        return reviewDao.deleteReview(reviewTitle, null) == 1;
    }
}
