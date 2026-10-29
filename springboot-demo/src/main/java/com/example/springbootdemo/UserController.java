package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.exception.BusinessException;
import com.example.springbootdemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Value("${file.upload.path}")
    private String uploadPath;

    @PostMapping("/upload/avatar")
    public Result <String> uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            throw new BusinessException(400,"文件不能为空");
        }
        //生成不重复的文件名（防止覆盖）
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString()+suffix;
        //确保目录存在
        File dir=new File(uploadPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //保存文件
        File dest=new File(uploadPath+newFileName);
        try{

            log.info("文件即将保存到：{}", dest.getAbsolutePath());
            file.transferTo(dest);
        }catch (IOException e){
            log.error("文件上次失败",e);
            throw new BusinessException(500,"上传失败");
        }
        //返回可直接访问的 URL
        String url="http://localhost:8080/uploads/"+newFileName;
        //实际业务中，这里通常还要更新当前用户的 avatar 字段
        String userId = (String) request.getAttribute("userId");

        log.info("文件上传成功：{}",url);
        return Result.success(url);


    }

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