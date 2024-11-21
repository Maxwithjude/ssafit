package com.ssafy.pjt.model.dto;

import java.util.Date;

public class Review {
	private String reviewTitle;
	private String reviewContent;
	private String createTime; // new Date().toString()으로 저장
	private int viewCnt;
    private int videoId; // 비디오 ID 추가
    private String userId;
    
    public Review() {
    	
    }
    
	public Review(String reviewTitle, String reviewContent, String createTime, int viewCnt, int videoId,
			String userId) {
		this.reviewTitle = reviewTitle;
		this.reviewContent = reviewContent;
		this.createTime = createTime;
		this.viewCnt = viewCnt;
		this.videoId = videoId;
		this.userId = userId;
	}
	
	public String getReviewTitle() {
		return reviewTitle;
	}
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    
}