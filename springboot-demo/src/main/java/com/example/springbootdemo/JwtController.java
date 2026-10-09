package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    @GetMapping("/generate")
    public Result<String> generate(@RequestParam Integer userId, @RequestParam String username) {
        String token = JwtUtil.generateToken(userId, username);
        return Result.success(token);
    }

    @GetMapping("/parse")
    public Result<Map<String, Object>> parse(@RequestParam String token) {
        Claims claims = JwtUtil.parseToken(token);
        Map<String, Object> result = new HashMap<>();
        result.put("userId", claims.getSubject());
        result.put("username", claims.get("username"));
        result.put("issuedAt", claims.getIssuedAt());
        result.put("expiration", claims.getExpiration());
        return Result.success(result);
    }
}