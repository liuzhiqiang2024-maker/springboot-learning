package com.example.springbootdemo.mapper;
import com.example.springbootdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper

public interface UserMapper {
    User findById(Integer id);
    List<User> findAll();
    int insert(User user);
    int update(User user);
    int deleteById(Integer id);
}
