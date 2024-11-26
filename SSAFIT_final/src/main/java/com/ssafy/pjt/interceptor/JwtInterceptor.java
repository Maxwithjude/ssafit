package com.ssafy.pjt.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.pjt.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final String HEADER_AUTH = "access-token";

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS 요청을 허용
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // Authorization 헤더에서 토큰을 가져옴
        String token = request.getHeader(HEADER_AUTH);

        // 토큰이 있으면 검증
        if (token != null) {
            try {
                // JWT 토큰을 검증하는 메소드 호출
                jwtUtil.validateToken(token);
                return true;
            } catch (Exception e) {
                // JWT 토큰이 유효하지 않으면 예외 처리
                throw new Exception("유효하지 않은 접근입니다.", e);
            }
        }

        // 토큰이 없으면 예외 발생
        throw new Exception("유효하지 않은 접근입니다.");
    }
}
