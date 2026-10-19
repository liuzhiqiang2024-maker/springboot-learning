package com.example.springbootdemo.service;
import com.example.springbootdemo.entity.Comment;


import java.util.List;
public interface CommentService {
    //发表评论
    Comment add(Comment comment);
    //获取某篇文章的评论树
    List<Comment> getCommentTree(Integer articleId);
}

