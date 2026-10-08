package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.exception.BusinessException;
import com.example.springbootdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User add(User user) {
        userMapper.insert(user);
        log.info("新增用户：{}", user);
        return user;
    }

    @Override
    public User update(Integer id, User user) {
        User target = findById(id);
        target.setName(user.getName());
        target.setAge(user.getAge());
        userMapper.update(target);
        log.info("修改用户：{}", target);
        return target;
    }

    @Override
    public void delete(Integer id) {
        findById(id);   // 确认存在
        userMapper.deleteById(id);
        log.info("删除用户：id = {}", id);
    }

    @Override
    public User findById(Integer id) {
        User user = userMapper.findById(id);
        if (user == null) {
            log.warn("用户不存在，id = {}", id);
            throw new BusinessException(404, "用户不存在，id = " + id);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
    @Override
    public List<User> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return userMapper.findByPage(offset, size);
    }

    @Override
    public List<User> findByNameLike(String keyword) {
        return userMapper.findByNameLike(keyword);
    }

    @Override
    public List<User> search(String name, Integer minAge, Integer maxAge) {
        return userMapper.search(name, minAge, maxAge);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transfer(Integer fromId, Integer toId, BigDecimal amount) {
        log.info("转账：{} -> {}，金额：{}", fromId, toId, amount);

        // 1. 扣钱
        int rows1 = userMapper.deductMoney(fromId, amount);
        if (rows1 == 0) {
            throw new BusinessException(400, "扣款失败");
        }

        // 2. 模拟异常
        if (amount.compareTo(new BigDecimal("10000")) > 0) {
            throw new BusinessException(400, "金额过大，转账失败");
        }

        // 3. 加钱
        int rows2 = userMapper.addMoney(toId, amount);
        if (rows2 == 0) {
            throw new BusinessException(400, "加款失败");
        }

        log.info("转账成功");
    }
}