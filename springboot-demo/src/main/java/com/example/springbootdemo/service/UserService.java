package com.example.springbootdemo.service;
import com.example.springbootdemo.entity.User;
import java.util.List;
public interface UserService {
    User add(User user);
    User update(Integer id, User user);
    void delete(Integer id);
    List<User> findAll();
    User findById(Integer id);
}
