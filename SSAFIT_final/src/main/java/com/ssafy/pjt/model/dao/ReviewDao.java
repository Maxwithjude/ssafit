package com.ssafy.pjt.model.dao;

import java.util.List;

import com.ssafy.pjt.model.dto.Review;

public interface ReviewDao {
	
	List<Review> selectAll();

	int writeReview(Review review);

	int modifyReview(Review review);

	int deleteReview(String title);
}
