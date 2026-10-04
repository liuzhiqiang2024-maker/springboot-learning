package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.exception.BusinessException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test-error")
    public Result<String> testError() {
        throw new BusinessException(400, "测试业务异常");
    }

    @GetMapping("/{id}")
    public Result<String> getUserById(@PathVariable int id) {
        if (id <= 0) {
            throw new BusinessException(400, "ID 必须大于 0");
        }
        return Result.success("查询用户 ID：" + id);
    }

    @GetMapping("/search")
    public Result<String> search(@RequestParam String keyword) {
        return Result.success("搜索关键字：" + keyword);
    }

    @PostMapping
    public Result<String> addUser(@RequestBody User user) {
        return Result.success("新增用户：" + user.getName() + "，年龄 " + user.getAge());
    }

    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable int id, @RequestBody User user) {
        return Result.success("修改 ID " + id + " 的用户为：" + user.getName());
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable int id) {
        return Result.success("删除用户 ID：" + id);
    }

    @GetMapping("/one")
    public Result<User> oneUser() {
        User u = new User();
        u.setName("张三");
        u.setAge(20);
        return Result.success(u);
    }

    @GetMapping("/list")
    public Result<List<User>> list() {
        List<User> list = new ArrayList<>();
        User u1 = new User(); u1.setName("张三"); u1.setAge(20);
        User u2 = new User(); u2.setName("李四"); u2.setAge(22);
        list.add(u1);
        list.add(u2);
        return Result.success(list);
    }
}