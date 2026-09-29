package com.example.springbootdemo;
import com.example.springbootdemo.entity.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
@RestController
@RequestMapping("/user")
public class UserController {
    //1.查路径参数
    @GetMapping("/{id}")
    public  String getUserById(@PathVariable int id){
        return "查询用户ID."+id;
    }
    //2.查URL参数
    @GetMapping("/search")
    public String search(@RequestParam String keyword){
        return "搜索关键字："+keyword;
    }
    //3.新增请求体
    @PostMapping
    public String addUser(@RequestBody User user){
        return "新增用户："+user.getName()+",年龄"+user.getAge();
    }
    //4.修改
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id,@RequestBody User user){
        return "修改ID."+id+"的用户为："+user.getName();
    }
    //5.删除
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id){
        return "删除用户ID."+id;
    }
    //6.返回对象
    @GetMapping("/one")
    public User oneUser(){
        User u= new User();
        u.setName("张三");
        u.setAge(20);
        return u;

    }
    //7.返回List
    @GetMapping("/list")
    public List<User> list(){
        List<User> list=new ArrayList<>();
        User u1 = new User(); u1.setName("张三"); u1.setAge(20);
        User u2 = new User(); u2.setName("李四"); u2.setAge(22);
        list.add(u1);
        list.add(u2);
        return list;
    }
}
