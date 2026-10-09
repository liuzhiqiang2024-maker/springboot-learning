package com.example.springbootdemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    // 密钥，至少 32 字符（HS256 要求）
    private static final String SECRET = "mySecretKeyForJwtToken1234567890";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 过期时间：1 小时
    private static final long EXPIRE_MS = 60 * 60 * 1000;

    /**
     * 生成 JWT
     */
    public static String generateToken(Integer userId, String username) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + EXPIRE_MS);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(now)
                .expiration(expire)
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析 JWT，返回 Claims
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从 token 取用户 ID
     */
    public static Integer getUserId(String token) {
        Claims claims = parseToken(token);
        return Integer.parseInt(claims.getSubject());
    }

    /**
     * 从 token 取用户名
     */
    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 判断 token 是否过期
     */
    public static boolean isExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}