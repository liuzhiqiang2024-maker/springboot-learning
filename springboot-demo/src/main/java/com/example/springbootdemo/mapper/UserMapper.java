package com.example.springbootdemo.mapper;
import com.example.springbootdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper

public interface UserMapper {
    User findById(Integer id);
    List<User> findAll();
    int insert(User user);
    int update(User user);
    int deleteById(Integer id);
    List<User> findByPage(@Param("offset")int offset,@Param("size")int size);
    List<User> findByNameLike(@Param("keyword")String keyword);
    List<User>search(@Param("name")String name,
                     @Param("minAge")Integer minAge,
                     @Param("maxAge")Integer maxAge);
}
