package com.ssafy.pjt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-user")
@Tag(name = "Login", description = "Login 관련 API")
public class LoginRestController {

	UserService us;

	public LoginRestController(UserService us) {
		this.us = us;
	}
	@Operation(summary = "사용자 로그인 처리")
	@PostMapping("login")
	public ResponseEntity<?> login(@ModelAttribute("User") User user) {
		User loginUser=null;
		try {
			loginUser = us.checkUser(user);
			
			if (loginUser == null) {
				return ResponseEntity.internalServerError().body("가입이 안된 유저입니다.");
			}
		} catch (Exception e) {
			ResponseEntity.badRequest().body("입력이 잘못되었습니다.");
		}
		return ResponseEntity.ok(loginUser);
	}
	@Operation(summary = "새로운 사용자 가입 처리")
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@ModelAttribute("User") User user){
		boolean result;
		try {
			result= us.signup(user);
		
			if(result) {
					return ResponseEntity.ok(user);
			}
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body("회원가입 실패");
		}
		return ResponseEntity.badRequest().body("회원가입 실패");
	}
}
