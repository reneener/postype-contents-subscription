package com.postype.sns.utill;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

	public static String getMemberId(String token, String key){
		return extractClaims(token, key).get("memberId", String.class);
	}
	public static boolean isExpired(String token, String key){
		Date expiredDate = extractClaims(token, key).getExpiration();
		return expiredDate.before(new Date()); //현재시각 보다 이전에 만료되었는지 체크
	}
	private static Claims extractClaims(String token, String key){
		return Jwts.parserBuilder().setSigningKey(getKey(key))
			.build().parseClaimsJws(token).getBody();
	}
	public static String generateToken(String memberId, String key, long expiredTimeMs){
		Claims claims = Jwts.claims();
		claims.put("memberId", memberId);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
			.signWith(getKey(key), SignatureAlgorithm.HS256)
			.compact();
	}

	private static Key getKey(String key){
		byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyByte);
	}
}
