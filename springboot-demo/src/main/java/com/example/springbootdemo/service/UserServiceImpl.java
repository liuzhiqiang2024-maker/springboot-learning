package com.example.springbootdemo.service;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final  AtomicInteger idGenerator = new AtomicInteger(1);
    private final  List<User> users = new ArrayList<>();

    public UserServiceImpl() {
        users.add(new User(idGenerator.getAndIncrement(), "张三", 20));
        users.add(new User(idGenerator.getAndIncrement(), "李四", 22));
    }
    @Override
    public User add(User user) {
        user.setId(idGenerator.getAndIncrement());
        users.add(user);
        log.info("新增用户：{}", user);
        return user;
    }

    @Override
    public User update(Integer id, User user) {
        User target = findById(id);
        target.setName(user.getName());
        target.setAge(user.getAge());
        log.info("修改用户：{}", target);
        return target;
    }

    @Override
    public void delete(Integer id) {
        User target = findById(id);
        users.remove(target);
        log.info("删除用户：id = {}", id);
    }

    @Override
    public User findById(Integer id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        log.warn("用户不存在，id = {}", id);
        throw new BusinessException(404, "用户不存在，id = " + id);
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
