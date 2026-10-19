package com.example.springbootdemo.mapper;
import  com.example.springbootdemo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface CommentMapper {
    //插入评论
    int insert(Comment comment);
    //根据文章ID查询所有评论
    List<Comment> findByArticleId(@Param("articleId") Integer articleId);
}
