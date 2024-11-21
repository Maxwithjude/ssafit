package com.ssafy.pjt.model.dto;

public class Video {
    private String videoId;
    private String title;
    private String description;
    private String url;
    private String publishedAt;
    private String thumbnailUrl;
     
    public Video() {}
    
	public Video(String videoId, String title, String description, String url, String publishedAt,
			String thumbnailUrl) {
		super();
		this.videoId = videoId;
		this.title = title;
		this.description = description;
		this.url = url;
		this.publishedAt = publishedAt;
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

    // Getters and Setters
    
}