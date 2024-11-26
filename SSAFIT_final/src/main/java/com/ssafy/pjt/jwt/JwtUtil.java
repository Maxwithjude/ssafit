package com.ssafy.pjt.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "SSAFY_NonMajor_JavaTrack_SecretKeySSAFY_NonMajor_JavaTrack_SecretKey"; // 최소 256비트 키
    private final SecretKey secretKey; // JWT 서명을 위한 SecretKey
    private final JwtParser jwtParser; // JWT 파서

    // 생성자에서 SecretKey 및 JwtParser 초기화
    public JwtUtil() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); // SecretKey 생성
        this.jwtParser = Jwts.parser()
                             .verifyWith(secretKey) // SecretKey를 설정
                             .build();
    }

	//토큰 생성시 다양한 데이터를 저장할 수 있음 (DTO or Map)
	public String createToken(String name, String nickname) {
		//유효기간 
		Date exp = new Date(System.currentTimeMillis()+ 1000*60*60); //1시간
		return Jwts.builder().header().add("typ", "JWT").and()
				.claim("name", name)
				.claim("nickname", nickname)
				.expiration(exp)
				.signWith(secretKey).compact();
	}

    // 모든 Claims 추출
    private Claims extractAllClaims(String token) {
        Jws<Claims> jws = jwtParser.parseSignedClaims(token); // 토큰에서 Claims 파싱
        return jws.getPayload(); // Claims 반환
    }

    // 특정 Claim 추출
    private <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 토큰에서 사용자 이름 추출
    public String extractName(String token) {
        return extractClaim(token, Claims::getSubject); // Subject에서 사용자 이름 추출
    }

    // 토큰에서 사용자 닉네임 추출
    public String extractNickname(String token) {
        return extractAllClaims(token).get("nickname", String.class); // "nickname" 클레임 추출
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token); // 토큰 파싱 성공 시 유효
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 에러 출력
            return false; // 유효하지 않은 토큰
        }
    }
}
