package com.book.heejinbook.security;

import com.book.heejinbook.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    private static String SECRET_KEY;

    @Value("${secret-key-source}")
    public void setKey(String value) {
        SECRET_KEY = value;
    }

    public static String createToken(User user) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        claims.put("userEmail", user.getEmail());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(claims)
                .setExpiration(expiryDate)
                .compact();
    }

    // Claims에서 loginId 꺼내기
    public static String getUserEmail(String token) {
        return extractClaims(token).get("userEmail").toString();
    }

    public static Long getUserId(String token) {
        return Long.valueOf(extractClaims(token).get("userId").toString());
    }

    // 밝급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

}
