package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/set")
    public Result<String> set(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return Result.success("已设置");
    }

    @GetMapping("/get")
    public Result<String> get(@RequestParam String key) {
        String value = redisTemplate.opsForValue().get(key);
        return Result.success(value);
    }

    @DeleteMapping("/del")
    public Result<String> del(@RequestParam String key) {
        redisTemplate.delete(key);
        return Result.success("已删除");
    }
}