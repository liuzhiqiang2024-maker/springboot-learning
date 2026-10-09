package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // ================== String ==================
    @GetMapping("/set")
    public Result<String> set(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return Result.success("已设置");
    }

    @GetMapping("/get")
    public Result<String> get(@RequestParam String key) {
        return Result.success(redisTemplate.opsForValue().get(key));
    }

    @GetMapping("/set-with-expire")
    public Result<String> setWithExpire(@RequestParam String key,
                                        @RequestParam String value,
                                        @RequestParam int seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        return Result.success("已设置，过期时间 " + seconds + " 秒");
    }

    @GetMapping("/incr")
    public Result<Long> incr(@RequestParam String key) {
        Long value = redisTemplate.opsForValue().increment(key);
        return Result.success(value);
    }

    @DeleteMapping("/del")
    public Result<String> del(@RequestParam String key) {
        redisTemplate.delete(key);
        return Result.success("已删除");
    }

    // ================== Hash ==================
    @PostMapping("/hash/set")
    public Result<String> hashSet(@RequestParam String key,
                                  @RequestParam String field,
                                  @RequestParam String value) {
        redisTemplate.opsForHash().put(key, field, value);
        return Result.success("已设置");
    }

    @GetMapping("/hash/get")
    public Result<Object> hashGet(@RequestParam String key, @RequestParam String field) {
        return Result.success(redisTemplate.opsForHash().get(key, field));
    }

    @GetMapping("/hash/all")
    public Result<Map<Object, Object>> hashAll(@RequestParam String key) {
        return Result.success(redisTemplate.opsForHash().entries(key));
    }

    // ================== List ==================
    @PostMapping("/list/push")
    public Result<String> listPush(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForList().rightPush(key, value);
        return Result.success("已追加");
    }

    @GetMapping("/list/all")
    public Result<List<String>> listAll(@RequestParam String key) {
        return Result.success(redisTemplate.opsForList().range(key, 0, -1));
    }

    @GetMapping("/list/pop")
    public Result<String> listPop(@RequestParam String key) {
        return Result.success(redisTemplate.opsForList().leftPop(key));
    }

    // ================== Set ==================
    @PostMapping("/set/add")
    public Result<String> setAdd(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForSet().add(key, value);
        return Result.success("已添加");
    }

    @GetMapping("/set/all")
    public Result<Set<String>> setAll(@RequestParam String key) {
        return Result.success(redisTemplate.opsForSet().members(key));
    }

    // ================== ZSet ==================
    @PostMapping("/zset/add")
    public Result<String> zsetAdd(@RequestParam String key,
                                  @RequestParam String value,
                                  @RequestParam double score) {
        redisTemplate.opsForZSet().add(key, value, score);
        return Result.success("已添加");
    }

    @GetMapping("/zset/top")
    public Result<Set<String>> zsetTop(@RequestParam String key, @RequestParam int n) {
        return Result.success(redisTemplate.opsForZSet().reverseRange(key, 0, n - 1));
    }
}