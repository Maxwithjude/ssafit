package com.ssafy.pjt.model.dto;

public class User {

	private String userId; // 아이디
	private String userNickname; // 이름
	private String userPassword; // 비밀번호
	private String userEmail;

	// 기본 생성자
	public User() {
	}
	
	
	
	public User(String userId, String userPassword) {
		this.userId = userId;
		this.userPassword = userPassword;
	}



	public User(String userId, String userNickname, String userPassword, String userEmail) {
		this.userId = userId;
		this.userNickname = userNickname;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
}
