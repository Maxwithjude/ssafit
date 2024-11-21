package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dao.ReviewDao;
import com.ssafy.pjt.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewDao rd;

	public ReviewServiceImpl(ReviewDao rd) {
		this.rd = rd;
	}

	@Override
	public List<Review> selectAll() {
		return rd.selectAll();
	}

	@Override
	public boolean writeReview(Review review) {
		return 1 == rd.writeReview(review);
	}

	@Override
	public boolean modifyReview(Review review) {
		return 1 == rd.modifyReview(review);
	}

	@Override
	public boolean deleteReview(String title) {
		return 1 == rd.deleteReview(title);
	}

}
