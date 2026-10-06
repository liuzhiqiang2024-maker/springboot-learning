package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}