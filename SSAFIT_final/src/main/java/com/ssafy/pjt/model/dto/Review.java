package com.ssafy.pjt.model.dto;

import java.time.LocalDateTime;

public class Review {
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime createTime; // 변경: LocalDateTime 사용
    private int viewCnt;
    private String videoId; // 변경: String 타입으로 수정
    private String userId;

    public Review() {}

    public Review(String reviewTitle, String reviewContent, LocalDateTime createTime, int viewCnt, String videoId,
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
