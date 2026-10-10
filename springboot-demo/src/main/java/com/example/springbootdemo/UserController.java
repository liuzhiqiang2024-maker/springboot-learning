package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 查询所有
    @GetMapping("/list")
    public Result<List<User>> list() {
        log.info("查询所有用户");
        return Result.success(userService.findAll());
    }

    // 根据 ID 查询
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Integer id) {
        log.info("查询用户，id = {}", id);
        return Result.success(userService.findById(id));
    }

    // 新增
    @PostMapping
    public Result<User> add(@Valid @RequestBody User user) {
        log.info("新增用户：{}", user.getName());
        return Result.success(userService.add(user));
    }

    @PostMapping("/transfer")
    public Result<Void> transfer(
            @RequestParam Integer fromId,
            @RequestParam Integer toId,
            @RequestParam BigDecimal amount) {
        log.info("转账请求：from={}, to={}, amount={}", fromId, toId, amount);
        userService.transfer(fromId, toId, amount);
        return Result.success();
    }

    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody User user) {
        log.info("注册请求：{}", user.getName());
        return Result.success(userService.register(user));
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        log.info("登录请求：{}", user.getName());
        String token = userService.login(user.getName(), user.getPassword());
        return Result.success(token);
    }

    // 修改
    @PutMapping("/{id}")
    public Result<User> update(@PathVariable Integer id, @Valid @RequestBody User user) {
        log.info("修改用户，id = {}", id);
        return Result.success(userService.update(id, user));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        log.info("删除用户，id = {}", id);
        userService.delete(id);
        return Result.success();
    }

    @GetMapping("/search-advanced")
    public  Result<List<User>> searchAdvanced(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        log.info("高级搜索：name={},mingAge={},maxgAge={}", name,minAge,maxAge);
        return Result.success(userService.search(name,minAge,maxAge));
    }

    @GetMapping("/search")
    public Result<List<User>> searchByName(@RequestParam String keyword) {
        log.info("模糊搜索：{}", keyword);
        return Result.success(userService.findByNameLike(keyword));
    }

    @GetMapping("/page")
    public Result<List<User>> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("分页查询：page={}, size={}", page, size);
        return Result.success(userService.findByPage(page, size));
    }
}