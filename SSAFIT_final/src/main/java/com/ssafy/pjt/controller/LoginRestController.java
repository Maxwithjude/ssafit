package com.ssafy.pjt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.jwt.JwtUtil;
import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-user")
@CrossOrigin("*")
@Tag(name = "Login", description = "Login 관련 API")
public class LoginRestController {

	private final UserService userService;
	private JwtUtil jwtUtil;

//	@Autowired
	public LoginRestController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	@Operation(summary = "사용자 로그인 처리")
	// 사용자 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
	    Map<String, Object> result = new HashMap<>();
	    HttpStatus status = null;
	    
	    // 로그 추가
	    System.out.println("Received user: " + user);
	    System.out.println("UserId: " + user.getUserId());
	    System.out.println("비번은:" +user.getUserPassword());
	    User loginUser = userService.checkUser(user.getUserId(), user.getUserPassword());
	    System.out.println("Login user: " + loginUser);
	    
	    if (loginUser != null) {
	        String name = loginUser.getUserName();
	        String nickname = loginUser.getUserNickname();
	        String token = jwtUtil.createToken(name, nickname);
	        result.put("message", "login 성공");
	        result.put("access-token", token);
	        status = HttpStatus.ACCEPTED;
	    } else {
	        result.put("message", "login 실패");
	        status = HttpStatus.UNAUTHORIZED;
	    }

	    return new ResponseEntity<>(result, status);
	}
	@Operation(summary = "새로운 사용자 가입 처리")
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user){
		boolean result;
		try {
			result= userService.signup(user);
		
			if(result) {
					return ResponseEntity.ok(user);
			}
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body("회원가입 실패");
		}
		return ResponseEntity.badRequest().body("회원가입 실패");
	}
	
	@Operation(summary = "로그인된 사용자 정보 반환")
    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            String actualToken = token.replace("Bearer ", "");
            String name = jwtUtil.extractName(actualToken);
            String nickname = jwtUtil.extractNickname(actualToken);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("name", name);
            userInfo.put("nickname", nickname);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }
    }
}
