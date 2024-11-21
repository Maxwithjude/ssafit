package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.Review;


public interface ReviewService {
    
	List<Review> selectAll();

	boolean writeReview(Review review);

	boolean modifyReview(Review review);

	boolean deleteReview(String title);
    
}
